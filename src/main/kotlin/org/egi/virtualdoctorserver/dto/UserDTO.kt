package org.egi.virtualdoctorserver.dto

import org.egi.virtualdoctorserver.model.User
import org.egi.virtualdoctorserver.model.NutritionValues

data class UserDTO(
    val user: User,
    val dietaryRequirements: NutritionValues
)