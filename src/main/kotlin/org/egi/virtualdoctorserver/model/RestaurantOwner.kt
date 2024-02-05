package org.egi.virtualdoctorserver.model

import jakarta.persistence.*

@Entity
@Table(name = "restaurant_owner")
data class RestaurantOwner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val username: String,
    val email: String
)

