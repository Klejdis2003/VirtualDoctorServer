package org.egi.virtualdoctorserver.model

import jakarta.annotation.Nonnull
import jakarta.persistence.*

@Entity
@Table(name = "restaurant_owner")
data class RestaurantOwner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Nonnull
    val username: String,
    @Nonnull
    val email: String
) {
    companion object {
        val VALUES = listOf(
            RestaurantOwner(
                username = "JohnDoe",
                email = "JohnDoe@gmail.com"
            ),
            RestaurantOwner(
                username = "JaneDoe",
                email = "JaneDoe@gmail.com"
            ),
            RestaurantOwner(
                username = "JohnSmith",
                email = "JohnSmith@gmail.com"
            )
        )
    }
}

