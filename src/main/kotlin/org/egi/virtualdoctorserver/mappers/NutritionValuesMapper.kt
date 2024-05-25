package org.egi.virtualdoctorserver.mappers

import org.egi.virtualdoctorserver.dto.NutritionValuesDTO
import org.egi.virtualdoctorserver.model.ItemNutritionValues
import org.egi.virtualdoctorserver.model.NutritionGoalValues
import org.egi.virtualdoctorserver.model.NutritionValues

class NutritionValuesMapper {
    fun toNutritionValuesDTO(nutritionValues: NutritionValues): NutritionValuesDTO {
        return NutritionValuesDTO(
            calories = nutritionValues.calories,
            protein = nutritionValues.protein,
            fat = nutritionValues.fat,
            carbohydrates = nutritionValues.carbohydrates
        )
    }

    fun toNutritionGoalValues(nutritionValuesDTO: NutritionValuesDTO, id: Long = 0): NutritionGoalValues {
        return NutritionGoalValues(
            id = id,
            calories = nutritionValuesDTO.calories,
            protein = nutritionValuesDTO.protein,
            fat = nutritionValuesDTO.fat,
            carbohydrates = nutritionValuesDTO.carbohydrates
        )
    }

    fun toItemNutritionValues(nutritionValuesDTO: NutritionValuesDTO): ItemNutritionValues {
        return ItemNutritionValues(
            calories = nutritionValuesDTO.calories,
            protein = nutritionValuesDTO.protein,
            fat = nutritionValuesDTO.fat,
            carbohydrates = nutritionValuesDTO.carbohydrates
        )
    }

    fun toNutritionValues(nutritionValuesDTO: NutritionValuesDTO): NutritionValues {
        return toItemNutritionValues(nutritionValuesDTO)
    }
}