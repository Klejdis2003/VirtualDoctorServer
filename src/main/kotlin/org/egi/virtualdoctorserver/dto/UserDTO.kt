package org.egi.virtualdoctorserver.dto

import org.egi.virtualdoctorserver.model.NutritionPlan

data class UserDTO(
    val username: String,
    val email: String,
    val age: Int,
    val height: Int,
    val weight: Int,
    val nutritionPlan: NutritionPlan,
    val nutritionType: NutritionTypeDTO
)
