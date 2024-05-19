package org.egi.virtualdoctorserver.services

import jakarta.annotation.PostConstruct
import org.egi.virtualdoctorserver.model.*
import org.egi.virtualdoctorserver.repositories.*
import org.springframework.stereotype.Service

@Service
class DataInitializationService(
    val dietTypeRepository: DietTypeRepository,
    val ingredientRepository: IngredientRepository,
    val restaurantOwnerRepository: RestaurantOwnerRepository,
    val restaurantRepository: RestaurantRepository,
    val itemRepository: ItemRepository
) {
    @PostConstruct
    fun initializeData() {
        initializeRestaurantOwners()
        initializeDietTypes()
        initializeIngredients()
        initializeRestaurants()
        initializeItems()
    }

    private fun initializeDietTypes() {
        if (dietTypeRepository.count() == 0L) dietTypeRepository.saveAll(NutritionType.VALUES)

    }

    private fun initializeIngredients() {
        if (ingredientRepository.count() == 0L) ingredientRepository.saveAll(Ingredient.VALUES)
    }

    private fun initializeRestaurantOwners() {
        if (restaurantOwnerRepository.count() == 0L) restaurantOwnerRepository.saveAll(RestaurantOwner.VALUES)
    }

    private fun initializeRestaurants() {
        if (restaurantRepository.count() == 0L) restaurantRepository.saveAll(Restaurant.VALUES)
    }

    private fun initializeItems() {
        if (itemRepository.count() == 0L) itemRepository.saveAll(Item.VALUES)
    }

}