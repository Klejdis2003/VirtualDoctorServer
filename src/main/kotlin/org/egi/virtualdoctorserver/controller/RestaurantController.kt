package org.egi.virtualdoctorserver.controller

import org.egi.virtualdoctorserver.model.DietaryRestrictions
import org.egi.virtualdoctorserver.model.Item
import org.egi.virtualdoctorserver.model.Restaurant
import org.egi.virtualdoctorserver.model.RestaurantItem
import org.egi.virtualdoctorserver.persistence.RestaurantItemRepository
import org.egi.virtualdoctorserver.persistence.RestaurantRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurants")
class RestaurantController(
    private val restaurantRepository: RestaurantRepository,
    private val restaurantItemRepository: RestaurantItemRepository
    ) {
    @GetMapping
    fun getAllRestaurants() = restaurantRepository.findAll().toList()

    @GetMapping("/{id}")
    fun getRestaurantById(@PathVariable("id") restaurantId: Int): ResponseEntity<Restaurant> {
        val restaurant = restaurantRepository.findById(restaurantId.toLong())
        return if (restaurant.isPresent) {
            ResponseEntity(restaurant.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/name={name}")
    fun getRestaurantByName(@PathVariable("name") name: String): List<Restaurant> {
        val restaurants = restaurantRepository.findByName(name)
        return restaurants
    }

    @GetMapping("/city={city}")
    fun getRestaurantByCity(@PathVariable("city") city: String): List<Restaurant> {
        val restaurants = restaurantRepository.findByCity(city)
        return restaurants
    }

    @GetMapping("/owner={ownerId}")
    fun getRestaurantByOwner(@PathVariable("ownerId") ownerId: Int): List<Restaurant> {
        val restaurants = restaurantRepository.findByOwnerId(ownerId.toLong())
        return restaurants
    }

    @GetMapping("/{id}/menu")
    fun getRestaurantMenu(@PathVariable("id") restaurantId: Int): ResponseEntity<List<Item>> {
        val restaurantItems = restaurantItemRepository.getRestaurantItemsByRestaurantId(restaurantId.toLong())
        return ResponseEntity(restaurantItems, HttpStatus.OK)
    }

    @GetMapping("/filteredItems")
    fun filterItemsByDietaryRestrictions(@RequestBody dietaryRestrictions: DietaryRestrictions): ResponseEntity<List<RestaurantItem>> {
        try{
            val restaurantItems = restaurantItemRepository.findAllItemsByDietaryRestrictions(dietaryRestrictions)
            return ResponseEntity(restaurantItems, HttpStatus.OK)
        }
        catch (e: Exception){
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{id}/filteredMenu")
    fun filterRestaurantMenuByDietaryRestrictions(@PathVariable("id") restaurantId: Int, @RequestBody dietaryRestrictions: DietaryRestrictions): ResponseEntity<List<Item>> {
        try {
            val restaurantItems =
                restaurantItemRepository.filterItemsByDietaryRestrictions(restaurantId.toLong(), dietaryRestrictions)
            return ResponseEntity(restaurantItems, HttpStatus.OK)
        }
        catch (e: Exception) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }
    @PostMapping("")
    fun createRestaurant(@RequestBody restaurant: Restaurant): ResponseEntity<Restaurant> {
        val createdRestaurant = restaurantRepository.save(restaurant)
        return ResponseEntity(createdRestaurant, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateRestaurant(@PathVariable("id") restaurantId: Int, @RequestBody restaurant: Restaurant): ResponseEntity<Restaurant> {
        val updatedRestaurant = restaurantRepository.save(restaurant)
        return ResponseEntity(updatedRestaurant, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteRestaurant(@PathVariable("id") restaurantId: Int): ResponseEntity<Unit> {
        restaurantRepository.deleteById(restaurantId.toLong())
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

}