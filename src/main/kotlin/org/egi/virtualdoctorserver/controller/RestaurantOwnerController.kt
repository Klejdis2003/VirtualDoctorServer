package org.egi.virtualdoctorserver.controller

import org.egi.virtualdoctorserver.model.RestaurantOwner
import org.egi.virtualdoctorserver.persistence.RestaurantOwnerRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurant_owners")

class RestaurantOwnerController(private val restaurantOwnerRepository: RestaurantOwnerRepository) {

    @GetMapping
    fun getAllRestaurantOwners() = restaurantOwnerRepository.findAll().toList()

    @GetMapping("/{id}")
    fun getRestaurantOwnerById(@PathVariable("id") restaurantOwnerId: Int): ResponseEntity<RestaurantOwner> {
        val restaurantOwner = restaurantOwnerRepository.findById(restaurantOwnerId.toLong())
        return if (restaurantOwner.isPresent) {
            ResponseEntity(restaurantOwner.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


    @GetMapping("find")
    fun getRestaurantOwnerByEmail(@RequestParam("email") email: String): ResponseEntity<RestaurantOwner> {
        val restaurantOwner = restaurantOwnerRepository.findByEmail(email)
        return if (restaurantOwner.isPresent) {
            ResponseEntity(restaurantOwner.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("")
    fun createRestaurantOwner(@RequestBody restaurantOwner: RestaurantOwner): ResponseEntity<RestaurantOwner> {
        val createdRestaurantOwner = restaurantOwnerRepository.save(restaurantOwner)
        return ResponseEntity(createdRestaurantOwner, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateRestaurantOwner(@PathVariable("id") restaurantOwnerId: Int, @RequestBody restaurantOwner: RestaurantOwner): ResponseEntity<RestaurantOwner> {
        val updatedRestaurantOwner = restaurantOwnerRepository.save(restaurantOwner)
        return ResponseEntity(updatedRestaurantOwner, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteRestaurantOwner(@PathVariable("id") restaurantOwnerId: Int): ResponseEntity<Unit> {
        restaurantOwnerRepository.deleteById(restaurantOwnerId.toLong())
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping("clear/key={key}")
    fun deleteAllRestaurantOwners(@PathVariable("key") key: String): ResponseEntity<Unit> {
        println("DELETE ALL RESTAURANT OWNERS, KEY: $key")
        if(key != "2003")
            return ResponseEntity.status(403).build()
        restaurantOwnerRepository.deleteAll()
        return ResponseEntity.status(200).build()
    }

}