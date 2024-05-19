package org.egi.virtualdoctorserver.services

import org.egi.virtualdoctorserver.model.NutritionValues
import org.egi.virtualdoctorserver.model.Item
import org.egi.virtualdoctorserver.model.NutritionType
import org.egi.virtualdoctorserver.repositories.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemRepository: ItemRepository
) {
    fun getAllItems() = itemRepository.findAll().toList()
    fun filterByDietaryRestrictions(dietaryRestrictions: NutritionValues): List<Item> {
//        return itemRepository.filterByDietaryRestrictions(
//            dietaryRestrictions.calories,
//            dietaryRestrictions.protein,
//            dietaryRestrictions.fat,
//            dietaryRestrictions.sugar,
//            dietaryRestrictions.isVegetarian,
//            dietaryRestrictions.isVegan
//        )
        TODO()
    }
    fun updateItem(item: Item) {
        itemRepository.save(item)
    }

    private fun addNutritionTypesToItem(item: Item){
        val nutritionTypes = NutritionType.VALUES

        nutritionTypes.forEach { nutritionType ->
            if(nutritionType.disallowedIngredientTypes.intersect(item.ingredients.toSet()).isEmpty())
                item.addNutritionType(nutritionType)
        }
    }
}