package org.egi.virtualdoctorserver.services

import org.egi.virtualdoctorserver.dto.ItemDTO
import org.egi.virtualdoctorserver.mappers.ItemMapper
import org.egi.virtualdoctorserver.model.DietaryRestrictions
import org.egi.virtualdoctorserver.model.Item
import org.egi.virtualdoctorserver.persistence.ItemRepository
import org.egi.virtualdoctorserver.persistence.RestaurantRepository
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class RestaurantService(
    private val restaurantRepository: RestaurantRepository,
    private val itemRepository: ItemRepository,
    private val itemMapper: ItemMapper
) {
    fun getAllRestaurants() = restaurantRepository.findAll().toList()
    fun getRestaurantMenu(restaurantId: Long): List<ItemDTO> {
        return itemRepository.getByRestaurantId(restaurantId)
            .map { itemMapper.toDTO(it) }
    }
    fun filterItemsByDietaryRestrictions(dietaryRestrictions: DietaryRestrictions): List<Item> {
        return itemRepository.findAllItemsByDietaryRestrictions(
            dietaryRestrictions.calorieLimit,
            dietaryRestrictions.maxProteinContent,
            dietaryRestrictions.maxFatContent,
            dietaryRestrictions.maxSugarContent,
            dietaryRestrictions.isVegetarian,
            dietaryRestrictions.isVegan
        )
    }

    fun addItemToMenu(restaurantId: Long, itemDTO: ItemDTO){
        val restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow { IllegalArgumentException("Restaurant with id $restaurantId does not exist") }
        val item = itemMapper.toEntity(itemDTO, restaurant)

        try {
            itemRepository.save(item)
        }

        catch (e: DataIntegrityViolationException) {
            when(e.cause){
                is ConstraintViolationException -> throw IllegalArgumentException("Exact item already exists in the menu.")
                else -> throw Exception("An error occurred while adding the item to the menu")
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}