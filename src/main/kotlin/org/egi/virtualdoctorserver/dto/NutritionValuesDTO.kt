package org.egi.virtualdoctorserver.dto

import org.egi.virtualdoctorserver.model.NutritionValues

data class NutritionValuesDTO(
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbohydrates: Int
)