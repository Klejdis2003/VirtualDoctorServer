package org.egi.virtualdoctorserver.dto

data class UserDietaryRequirementsDTO(
    val calorieLimit: Int,
    val maxSugarContent: Int,
    val maxFatContent: Int,
    val maxProteinContent: Int,
    val isVegetarian: Boolean,
    val isVegan: Boolean
)