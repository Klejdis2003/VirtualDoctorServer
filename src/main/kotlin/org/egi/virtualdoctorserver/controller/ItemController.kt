package org.egi.virtualdoctorserver.controller

import org.egi.virtualdoctorserver.dto.ItemDTOWithRestaurant
import org.egi.virtualdoctorserver.exceptions.InvalidQueryParamException
import org.egi.virtualdoctorserver.model.Ingredient
import org.egi.virtualdoctorserver.services.ItemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RequestMapping("/items")
@RestController
class ItemController(
    private val itemService: ItemService
) {
    @GetMapping("")
    fun getAllItems(@RequestParam filters: Map<String, String>): ResponseEntity<List<ItemDTOWithRestaurant>> {
        println("GET /items - $filters")
        return try {
            ResponseEntity.ok(itemService.getAll(filters))
        } catch (e : InvalidQueryParamException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing the request.")
        }
    }

    @GetMapping("/{id}")
    fun getItem(@PathVariable("id") itemId: Long): ResponseEntity<ItemDTOWithRestaurant> {
        return try {
            ResponseEntity.ok(itemService.get(itemId))
        } catch (e: Exception) {
            ResponseEntity.status(500).build()
        }
    }

    @GetMapping("/ingredients")
    fun searchIngredients(@RequestParam("name") name: String): ResponseEntity<List<Ingredient>> {
        println("GET /items/ingredients - $name")
        return try {
            ResponseEntity.ok(itemService.searchIngredients(name))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing the request.")
        }
    }

    @GetMapping("/help")
    fun help(): String {
        return itemService.help()
    }
}