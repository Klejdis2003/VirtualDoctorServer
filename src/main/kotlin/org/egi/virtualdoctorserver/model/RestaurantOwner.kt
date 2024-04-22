package org.egi.virtualdoctorserver.model

import jakarta.annotation.Nonnull
import jakarta.persistence.*

@Entity
@Table(name = "restaurant_owner")
data class RestaurantOwner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Nonnull
    val username: String,
    @Nonnull
    val email: String
)

