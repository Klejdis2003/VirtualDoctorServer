package org.egi.virtualdoctorserver.controller

import org.egi.virtualdoctorserver.model.NutritionValues
import org.egi.virtualdoctorserver.model.Item
import org.egi.virtualdoctorserver.services.ItemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/items")
@RestController
class ItemController(
    private val itemService: ItemService
) {
    @GetMapping("")
    fun getAllItems(): ResponseEntity<List<Item>> {
        return try {
            ResponseEntity.ok(itemService.getAllItems())
        } catch (e: Exception) {
            ResponseEntity.status(500).build()
        }
    }
    @GetMapping("/filteredItems")
    fun filterItemsByDietaryRestrictions(@RequestBody nutritionValues: NutritionValues): ResponseEntity<List<Item>> {
       println("GET /items/filteredItems")
        return try {
            ResponseEntity.ok(
                itemService.filterByDietaryRestrictions(nutritionValues)
            )
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.status(500).build()
        }
    }
}