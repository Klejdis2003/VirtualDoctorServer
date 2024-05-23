package org.egi.virtualdoctorserver.services

import org.egi.virtualdoctorserver.controller.NutritionTypeMapper
import org.egi.virtualdoctorserver.dto.UserDTO
import org.egi.virtualdoctorserver.exceptions.ConflictException
import org.egi.virtualdoctorserver.exceptions.NoPermissionException
import org.egi.virtualdoctorserver.exceptions.NotFoundException
import org.egi.virtualdoctorserver.mappers.UserMapper
import org.egi.virtualdoctorserver.model.NutritionGoalValues
import org.egi.virtualdoctorserver.model.NutritionPlan
import org.egi.virtualdoctorserver.model.NutritionPlanType
import org.egi.virtualdoctorserver.model.NutritionValues
import org.egi.virtualdoctorserver.repositories.NutritionPlanRepository
import org.egi.virtualdoctorserver.repositories.NutritionTypeRepository
import org.egi.virtualdoctorserver.repositories.UserItemRepository
import org.egi.virtualdoctorserver.repositories.UserRepository
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.jvm.Throws

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userItemRepository: UserItemRepository,
    private val nutritionTypeRepository: NutritionTypeRepository,
    private val nutritionPlanRepository: NutritionPlanRepository
) {
    private val userMapper = UserMapper(NutritionTypeMapper(nutritionTypeRepository))

    private fun handleConstraintViolationException(e: ConstraintViolationException): ConflictException {
        val message = e.message!!
        val fields = listOf("email", "username")
        if(fields.any { message.contains("Key ($it)") } && message.contains("duplicate")) {
            val conflictField = fields.first { message.contains("Key ($it)") }.replaceFirstChar { it.uppercase() }
            throw ConflictException("$conflictField already exists. Try a different one.")
        }

        throw ConflictException("An error occurred while saving the user")
    }
    fun getAll(): List<UserDTO> = userRepository.findAll().map { userMapper.toUserDTO(it) }

    fun get(id: Long): UserDTO? =
        userRepository.findById(id).map { userMapper.toUserDTO(it) }.orElse(null)

    fun getByEmail(email: String): UserDTO? =
        userRepository.findByEmail(email).map { userMapper.toUserDTO(it) }.orElse(null)

    fun getByUsername(username: String): UserDTO? =
        userRepository.findByUsername(username).map { userMapper.toUserDTO(it) }.orElse(null)

    /**
     * Saves a user to the database
     * @param user the user to save
     * @return the saved user, with updated primary and foreign keys
     */
    fun createUser(userDTO: UserDTO): UserDTO {
        try {
            if(userDTO.nutritionPlan.id == 0L)
                nutritionPlanRepository.save(userDTO.nutritionPlan)
            else if(userDTO.nutritionType.id < 0L)
                throw IllegalArgumentException("Valid ids start from 1.")

            val user = userMapper.toUser(userDTO)
            val createdUser = userRepository.save(user)
            return userMapper.toUserDTO(createdUser)
        } catch (e: DataIntegrityViolationException) {
            when(e.cause){
                is ConstraintViolationException -> {
                    throw handleConstraintViolationException(e.cause as ConstraintViolationException)
                }
                else -> throw Exception("A constraint violation occurred. Please check the user data and try again.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("An unknown error occurred. Please try again.")
        }
    }

    @Throws(NotFoundException::class, UnsupportedOperationException::class)
    fun update(userDTO: UserDTO): UserDTO {
        if (!userRepository.existsByEmail(userDTO.email))
            throw NotFoundException("User with email ${userDTO.email} does not exist")

        val actualUserDB = userRepository.findByEmail(userDTO.email).get()
        if (userDTO.nutritionPlan != actualUserDB.nutritionPlan)
            throw UnsupportedOperationException("Changing the nutrition is not supported through this method. Please use the appropriate endpoint.")

        val user = userMapper.toUser(userDTO)
        val updatedUser = userRepository.save(user)
        return userMapper.toUserDTO(updatedUser)

    }

    @Throws(NotFoundException::class)
    fun delete(id: Long): Boolean{
        if(!userRepository.existsById(id))
            throw NotFoundException("User with id $id does not exist")

        return try {
            userRepository.deleteById(id)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    @Throws(NoPermissionException::class)
    fun deleteAll(key: String) {
        if(key == System.getenv("ADMIN_KEY"))
            userRepository.deleteAll()

        throw NoPermissionException("Invalid key provided. No permission to delete all users.")
    }

    fun getDailyStats(userId: Long): NutritionValues {
        val date = LocalDate.now().atStartOfDay().toLocalDate()
        val dailyUserItems = userItemRepository.getByUserIdAndDateBetween(userId, date, date)
        val stats = NutritionGoalValues.buildFromUserItemList(dailyUserItems)
        return stats
    }

    fun getMonthlyStats(userId: Long): NutritionValues {
        val startDate = LocalDate.now().withDayOfMonth(1)
        val endDate = startDate.plusMonths(1).minusDays(1)
        val monthlyUserItems = userItemRepository.getByUserIdAndDateBetween(userId, startDate, endDate)
        val monthlyStats = NutritionGoalValues.buildFromUserItemList(monthlyUserItems)
        return monthlyStats
    }

    /**
     * Add an item to a user's list of consumed items
     * @param userId the id of the userository.save(item)
     * @param itemId the id of the item
     * @return the updated stats of the user for the day
     */
    fun addUserItem(userId: Long, itemId: Long): NutritionValues{
        userItemRepository.save(userId, itemId)
        return getDailyStats(userId)
    }

    fun updateNutritionGoalToPredefined(username: String, planId: Long): UserDTO {
        var user = userRepository.findByUsername(username).orElseThrow { NotFoundException("User with username $username does not exist") }
        val plan = nutritionPlanRepository.findById(planId).orElseThrow { NotFoundException("Nutrition plan with id $planId does not exist") }
        user = user.copy(nutritionPlan = plan)
        val updatedUser = userRepository.save(user)
        return userMapper.toUserDTO(updatedUser)
    }

    fun updateNutritionGoalToCustom(username: String, customNutritionPlan: NutritionPlan): UserDTO {
        var user = userRepository.findByUsername(username).orElseThrow { NotFoundException("User with username $username does not exist") }
        val previousNutritionPlan = user.nutritionPlan

        user = user.copy(nutritionPlan = customNutritionPlan)

        nutritionPlanRepository.save(customNutritionPlan)

        val updatedUser = userRepository.save(user)
        if(previousNutritionPlan.type == NutritionPlanType.CUSTOM)
            nutritionPlanRepository.delete(previousNutritionPlan)

        return userMapper.toUserDTO(updatedUser)
    }

}