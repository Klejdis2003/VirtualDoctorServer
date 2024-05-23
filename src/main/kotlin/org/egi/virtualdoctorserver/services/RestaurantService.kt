package org.egi.virtualdoctorserver.services

import org.egi.virtualdoctorserver.dto.ItemDTO
import org.egi.virtualdoctorserver.mappers.ItemMapper
import org.egi.virtualdoctorserver.model.NutritionValues
import org.egi.virtualdoctorserver.model.Item
import org.egi.virtualdoctorserver.model.Restaurant
import org.egi.virtualdoctorserver.repositories.ItemRepository
import org.egi.virtualdoctorserver.repositories.RestaurantRepository
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

    fun getById(restaurantId: Long): Restaurant? {
        return restaurantRepository.getById(restaurantId)
    }

    fun filterByCity(city: String): List<Restaurant> {
        return restaurantRepository.findByCity(city)
    }

    fun filterByOwner(ownerId: Long): List<Restaurant> {
        return restaurantRepository.findByOwnerId(ownerId)
    }


    fun getRestaurantMenu(restaurantId: Long): List<ItemDTO> {
        return itemRepository.getByRestaurantId(restaurantId).map { itemMapper.toItemDTO(it) }
    }
    fun filterItemsByDietaryRestrictions(dietaryValues: NutritionValues): List<Item> {
//        return itemRepository.filterByDietaryRestrictions(
//            dietaryValues.calories,
//            dietaryValues.protein,
//            dietaryValues.fat,
//            dietaryValues.sugar,
//        )
        TODO()
    }

    fun addItemToMenu(restaurantId: Long, itemDTO: ItemDTO){
        val restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow { IllegalArgumentException("Restaurant with id $restaurantId does not exist") }
        val item = itemMapper.toItem(itemDTO)

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

    fun save(restaurant: Restaurant): Restaurant {
        return restaurantRepository.save(restaurant)
    }

    fun delete(restaurantId: Long) {
        restaurantRepository.deleteById(restaurantId)
    }
    fun delete(restaurant: Restaurant) {
        restaurantRepository.delete(restaurant)
    }
}