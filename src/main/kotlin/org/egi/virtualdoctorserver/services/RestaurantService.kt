package org.egi.virtualdoctorserver.services

import org.egi.virtualdoctorserver.dto.ItemDTO
import org.egi.virtualdoctorserver.dto.RestaurantDTO
import org.egi.virtualdoctorserver.exceptions.InvalidQueryParamException
import org.egi.virtualdoctorserver.filters.ItemFilter
import org.egi.virtualdoctorserver.mappers.ItemMapper
import org.egi.virtualdoctorserver.mappers.RestaurantMapper
import org.egi.virtualdoctorserver.model.Item
import org.egi.virtualdoctorserver.model.Restaurant
import org.egi.virtualdoctorserver.repositories.ItemRepository
import org.egi.virtualdoctorserver.repositories.RestaurantRepository
import org.springframework.stereotype.Service

@Service
class RestaurantService(
    private val restaurantRepository: RestaurantRepository,
    private val itemRepository: ItemRepository,
    private val itemMapper: ItemMapper
) {
    private val restaurantMapper = RestaurantMapper(itemMapper)
    private val itemFilter = ItemFilter()

    fun getAllRestaurants(): List<RestaurantDTO> {
        val restaurants = restaurantRepository.findAll()
        return restaurants.map { restaurantMapper.toRestaurantDTO(it, getRestaurantMenu(it.id)) }
    }

    fun getById(restaurantId: Long): RestaurantDTO? {
        return restaurantRepository
            .findById(restaurantId)
            .map { restaurantMapper.toRestaurantDTO(it, getRestaurantMenu(it.id)) }
            .orElse(null)
    }

    fun filterByCity(city: String): List<RestaurantDTO> {
        return restaurantRepository
            .findByCity(city)
            .map { restaurantMapper.toRestaurantDTO(it, getRestaurantMenu(it.id)) }
    }

    fun filterByOwner(ownerId: Long): List<RestaurantDTO> {
        return restaurantRepository
            .findByOwnerId(ownerId)
            .map { restaurantMapper.toRestaurantDTO(it, getRestaurantMenu(it.id)) }
    }

    /**
     * Retrieves the menu for a given restaurant. Optionally filters the menu by nutrition type and price.
     * @param restaurantId the id of the restaurant
     * @param filters a map of filters to apply to the menu. Valid filters are defined in the ItemFilter enum.
     * An example of a valid filters object
     * {
     *     "nutrition_type": "Vegan",
     *     "price": "10.0"
     * }
     * We do not have to use both of them necessarily, for example:
     * {
     *   "price": "10.0"
     * }
     * Example of an invalid filters object:
     * {
     * "whatever": x
     * }
     * If any filter not part of the defined ones is present, the method is made to fail.
     * @throws IllegalArgumentException if an invalid filter is provided
     * @return a list of items that belong to the restaurant with the given id
     */
    fun getRestaurantMenu(restaurantId: Long, filters: Map<String, String> = emptyMap()): List<ItemDTO> {
        var menu = itemRepository.getByRestaurantId(restaurantId)
        try {
            menu = itemFilter.filter(menu, filters)
            return menu.map { itemMapper.toItemDTO(it) }
        }
        catch(_ : NoSuchElementException) {
            throw InvalidQueryParamException("Invalid filter provided. Valid filters are: ${itemFilter.filterTypes.map { it.filterName }}")
        }
    }


    private fun filterRestaurantMenuByNutritionType(restaurantId: Long, nutritionTypeName: String): List<Item> {
        return itemRepository.findByNutritionTypeNameAndRestaurant(nutritionTypeName, restaurantId)
    }

    /**
     * Adds an item to the menu of a restaurant ( collection of items that belong to it)
     * @param restaurantId the id of the restaurant
     * @param itemDTO the item to be added, represented as a DTO
     * @throws IllegalArgumentException if the restaurant does not exist
     */
    fun addItemToMenu(restaurantId: Long, itemDTO: ItemDTO): Collection<ItemDTO>{
        val restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow { IllegalArgumentException("Restaurant with id $restaurantId does not exist") }
        val item = itemMapper.toItem(itemDTO, restaurant)
        itemRepository.save(item)
        return getRestaurantMenu(restaurantId)
    }

    /**
     * Saves a restaurant to the database.
     * @param restaurant the restaurant to be saved
     * @return the saved restaurant, with updated id
     */
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