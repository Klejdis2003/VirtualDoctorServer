package org.egi.virtualdoctorserver.model

import jakarta.persistence.*

@Entity
@Table(name = "user_dietary_requirements")
data class DietaryRestrictions(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val calorieLimit: Int = NO_LIMIT,
    val maxSugarContent: Int = NO_LIMIT,
    val maxFatContent: Int = NO_LIMIT,
    val maxProteinContent: Int = NO_LIMIT,
    val isVegetarian: Boolean = false,
    val isVegan: Boolean = false
)