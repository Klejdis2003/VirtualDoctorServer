package org.egi.virtualdoctorserver.dto

import org.egi.virtualdoctorserver.model.Ingredient
import org.egi.virtualdoctorserver.model.ItemType
import org.egi.virtualdoctorserver.model.NutritionValues
import org.egi.virtualdoctorserver.model.Restaurant

data class ItemDTO(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Float,
    val type: ItemType,
    val nutritionValues: NutritionValuesDTO,
    val ingredients: List<Ingredient>,
)

data class ItemDTOWithRestaurant(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Float,
    val type: ItemType,
    val nutritionValues: NutritionValuesDTO,
    val ingredients: List<Ingredient>,
    val restaurant: Restaurant
)