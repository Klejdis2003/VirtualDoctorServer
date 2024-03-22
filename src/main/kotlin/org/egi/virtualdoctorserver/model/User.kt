package org.egi.virtualdoctorserver.model

import jakarta.persistence.*;

const val NO_LIMIT = Integer.MAX_VALUE
@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(unique = true)
    val email: String,
    @Column(unique = true)
    val username: String,

    val age: Int,
    val height: Float, //height in cm
    val weight: Float, //weight in kg
    val calorieLimit: Int = NO_LIMIT,
    val maxSugarContent: Int = NO_LIMIT,
    val maxFatContent: Int =  NO_LIMIT,
    val maxProteinContent: Int = NO_LIMIT,
    val isVegetarian: Boolean = false,
    val isVegan: Boolean = false,
//    val glutenFree: Boolean = false,
//    val nutFree: Boolean = false,
//    val dairyFree: Boolean = false,
//    val halal: Boolean = false,
//    val kosher: Boolean = false,
    )