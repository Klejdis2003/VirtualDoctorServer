package org.egi.virtualdoctorserver.services

import org.egi.virtualdoctorserver.model.Stats
import org.egi.virtualdoctorserver.model.User
import org.egi.virtualdoctorserver.model.UserItem
import org.egi.virtualdoctorserver.persistence.DietaryRestrictionsRepository
import org.egi.virtualdoctorserver.persistence.UserItemRepository
import org.egi.virtualdoctorserver.persistence.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val dietaryRestrictionsRepository: DietaryRestrictionsRepository,
    private val userItemRepository: UserItemRepository
) {
    /**
     * Saves a user to the database
     * @param user the user to save
     * @return the saved user, with updated primary and foreign keys
     */
    fun saveUser(user: User): User {
        val dietaryRequirements = user.dietaryRequirements
        val createdDietaryRestrictions = dietaryRestrictionsRepository.save(dietaryRequirements)
        try {
            val createdUser = userRepository.save(user.copy(dietaryRequirements = createdDietaryRestrictions))
            return createdUser
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("An error occurred while saving the user")
        }
    }

    fun getDailyStats(userId: Long): Stats {
        val date = LocalDate.now().atStartOfDay().toLocalDate()
        val dailyUserItems = userItemRepository.getByUserIdAndDateBetween(userId, date, date)
        val stats = Stats.buildFromUserItemList(dailyUserItems)
        return stats
    }

    fun getMonthlyStats(userId: Long): Stats {
        val startDate = LocalDate.now().withDayOfMonth(1)
        val endDate = startDate.plusMonths(1).minusDays(1)
        val monthlyUserItems = userItemRepository.getByUserIdAndDateBetween(userId, startDate, endDate)
        val monthlyStats = Stats.buildFromUserItemList(monthlyUserItems)
        return monthlyStats
    }

    /**
     * Add an item to a user's list of consumed items
     * @param userId the id of the user
     * @param itemId the id of the item
     * @return the updated stats of the user for the day
     */
    fun addUserItem(userId: Long, itemId: Long): Stats{
        userItemRepository.save(userId, itemId)
        return getDailyStats(userId)
    }
}