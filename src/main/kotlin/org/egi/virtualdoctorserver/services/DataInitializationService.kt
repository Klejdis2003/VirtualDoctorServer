package org.egi.virtualdoctorserver.services

import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.egi.virtualdoctorserver.model.*
import org.egi.virtualdoctorserver.repositories.*
import org.springframework.stereotype.Service

@Service
class DataInitializationService(
    val nutritionTypeRepository: NutritionTypeRepository,
    val ingredientRepository: IngredientRepository,
    val restaurantOwnerRepository: RestaurantOwnerRepository,
    val restaurantRepository: RestaurantRepository,
    val itemRepository: ItemRepository,
    val nutritionPlanRepository: NutritionPlanRepository,
    val userRepository: UserRepository
) {
    @PostConstruct
    @Transactional
    fun initializeData() {
        initializeRestaurantOwners()
        initializeDietTypes()
        initializeIngredients()
        initializeRestaurants()
        initializeItems()
        initializeNutritionGoalPlans()
        initializeUsers()
    }

    private fun initializeDietTypes() {
        if (nutritionTypeRepository.count() == 0L) nutritionTypeRepository.saveAll(NutritionType.VALUES)

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

    private fun initializeNutritionGoalPlans() {
        if (nutritionPlanRepository.count() == 0L) nutritionPlanRepository.saveAll(NutritionPlan.PLANS)
    }

    fun initializeUsers() {
        val nutritionalPlans = nutritionPlanRepository.findAll().toList()
        val nutritionTypes = nutritionTypeRepository.findAll().toList()
        val vals = User.VALUES.map { it.copy(nutritionPlan = nutritionalPlans.random(), nutritionType = nutritionTypes.random()) }
        if (userRepository.count() == 0L) userRepository.saveAll(vals)
    }

}