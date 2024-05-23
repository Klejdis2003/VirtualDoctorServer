package org.egi.virtualdoctorserver.dto

import org.egi.virtualdoctorserver.model.NutritionPlan

data class UserDTO(
    val email: String,
    val username: String,
    val age: Int,
    val height: Int,
    val weight: Int,
    val nutritionPlan: NutritionPlan,
    val nutritionType: NutritionTypeDTO
)
