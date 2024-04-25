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

    @JoinColumn(name = "dietary_requirements_id", nullable = false)
    @OneToOne(cascade = [CascadeType.ALL])
    val dietaryRequirements: DietaryRestrictions
    )

