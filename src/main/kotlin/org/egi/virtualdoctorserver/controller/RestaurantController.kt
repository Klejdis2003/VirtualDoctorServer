package org.egi.virtualdoctorserver.controller

import org.egi.virtualdoctorserver.dto.ItemDTO
import org.egi.virtualdoctorserver.model.NutritionValues
import org.egi.virtualdoctorserver.model.Item
import org.egi.virtualdoctorserver.model.Restaurant
import org.egi.virtualdoctorserver.services.RestaurantService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurants")
class RestaurantController(
    private val restaurantService: RestaurantService
    ) {
    @GetMapping
    fun getAllRestaurants() = restaurantService.getAllRestaurants()

    @GetMapping("/{id}")
    fun getRestaurantById(@PathVariable("id") restaurantId: Long): ResponseEntity<Restaurant> {
        val restaurant = restaurantService.getById(restaurantId)
        return restaurant?.let { ResponseEntity(it, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }
    
    @GetMapping("/city={city}")
    fun getRestaurantByCity(@PathVariable("city") city: String): List<Restaurant> {
        val restaurants = restaurantService.filterByCity(city)
        return restaurants
    }

    @GetMapping("/owner={ownerId}")
    fun getRestaurantByOwner(@PathVariable("ownerId") ownerId: Long): List<Restaurant> {
        val restaurants = restaurantService.filterByOwner(ownerId)
        return restaurants
    }

    @GetMapping("/{id}/menu")
    fun getRestaurantMenu(@PathVariable("id") restaurantId: Long): ResponseEntity<List<ItemDTO>> {
        val restaurantItems = restaurantService.getRestaurantMenu(restaurantId)
        return ResponseEntity(restaurantItems, HttpStatus.OK)
    }

    @PostMapping("/{id}/menu")
    fun addItemToMenu(@PathVariable("id") restaurantId: Long, @RequestBody itemDTO: ItemDTO): ResponseEntity<List<ItemDTO>> {
        try {
            restaurantService.addItemToMenu(restaurantId, itemDTO)
            return ResponseEntity(restaurantService.getRestaurantMenu(restaurantId), HttpStatus.CREATED)
        }
        catch (e: IllegalArgumentException) {
            return ResponseEntity(HttpStatus.CONFLICT)
        }
        catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/filteredItems")
    fun filterItemsByDietaryRestrictions(@RequestBody dietaryRestrictions: NutritionValues): ResponseEntity<List<Item>> {
        try{
            val restaurantItems = restaurantService.filterItemsByDietaryRestrictions(dietaryRestrictions)
            return ResponseEntity(restaurantItems, HttpStatus.OK)
        }
        catch (e: Exception){
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}/filteredMenu")
    fun filterRestaurantMenuByDietaryRestrictions(@PathVariable("id") restaurantId: Long, @RequestBody nutritionValues: NutritionValues): ResponseEntity<List<Item>> {
        try {
            val restaurantItems = restaurantService.filterItemsByDietaryRestrictions(nutritionValues)
            return ResponseEntity(restaurantItems, HttpStatus.OK)
        }
        catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }
    @PostMapping("")
    fun createRestaurant(@RequestBody restaurant: Restaurant): ResponseEntity<Restaurant> {
        val createdRestaurant = restaurantService.save(restaurant)
        return ResponseEntity(createdRestaurant, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateRestaurant(@PathVariable("id") restaurantId: Long, @RequestBody restaurant: Restaurant): ResponseEntity<Restaurant> {
        val updatedRestaurant = restaurantService.save(restaurant)
        return ResponseEntity(updatedRestaurant, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteRestaurant(@PathVariable("id") restaurantId: Long): ResponseEntity<Unit> {
        restaurantService.delete(restaurantId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

}