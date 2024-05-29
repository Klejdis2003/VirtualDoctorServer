package org.egi.virtualdoctorserver.controller

import org.egi.virtualdoctorserver.dto.ItemDTO
import org.egi.virtualdoctorserver.dto.RestaurantDTO
import org.egi.virtualdoctorserver.exceptions.InvalidQueryParamException
import org.egi.virtualdoctorserver.model.Restaurant
import org.egi.virtualdoctorserver.services.RestaurantService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/restaurants")
class RestaurantController(
    private val restaurantService: RestaurantService
    ) {
    @GetMapping
    fun getAllRestaurants(): ResponseEntity<List<RestaurantDTO>>  {
        return try {
            ResponseEntity.ok(restaurantService.getAllRestaurants())
        } catch (e: Exception) {
            ResponseEntity.status(500).build()
        }
    }

    @GetMapping("/{id}")
    fun getRestaurantById(@PathVariable("id") restaurantId: Long): ResponseEntity<RestaurantDTO> {
        val restaurant = restaurantService.getById(restaurantId)
        return restaurant?.let { ResponseEntity(it, HttpStatus.OK) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }
    
    @GetMapping("/city={city}")
    fun getRestaurantByCity(@PathVariable("city") city: String): List<RestaurantDTO> {
        val restaurants = restaurantService.filterByCity(city)
        return restaurants
    }

    @GetMapping("/owner={ownerId}")
    fun getRestaurantByOwner(@PathVariable("ownerId") ownerId: Long): List<RestaurantDTO> {
        val restaurants = restaurantService.filterByOwner(ownerId)
        return restaurants
    }

    @GetMapping("/{id}/menu")
    fun getRestaurantMenu(@PathVariable("id") restaurantId: Long, @RequestParam filters : Map<String, String>): ResponseEntity<List<ItemDTO>> {
        println("GET/restaurants/$restaurantId/menu with filters: $filters")
        return try {
            val menu = restaurantService.getRestaurantMenu(restaurantId, filters)
            ResponseEntity(menu, HttpStatus.OK)
        } catch (e: InvalidQueryParamException) {
            e.printStackTrace()
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid number format in query parameter.")
        } catch (e: Exception) {
            e.printStackTrace()
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing the request.")
        }
    }

    @PostMapping("/{id}/menu")
    fun addItemToMenu(@PathVariable("id") restaurantId: Long, @RequestBody itemDTO: ItemDTO): ResponseEntity<List<ItemDTO>> {
        println("POST/restaurants/$restaurantId/menu - $itemDTO")
        try {
            restaurantService.addItemToMenu(restaurantId, itemDTO)
            return ResponseEntity(restaurantService.getRestaurantMenu(restaurantId), HttpStatus.CREATED)
        }
        catch (e: IllegalArgumentException) {
            return ResponseEntity(HttpStatus.CONFLICT)
        }
        catch (e: Exception) {
            e.printStackTrace()
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