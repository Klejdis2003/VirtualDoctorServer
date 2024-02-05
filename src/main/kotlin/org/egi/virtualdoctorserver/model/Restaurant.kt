package org.egi.virtualdoctorserver.model

import jakarta.persistence.*

@Entity
@Table(name = "restaurant")
data class Restaurant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val street_address: String,
    val city: String,
    val postcode: String,
    val country: String,
    val telephone: String,
    val email: String,
    val website: String,
    @JoinColumn(name = "owner_id", nullable = false)
    @OneToOne(cascade = [CascadeType.ALL])
    val owner: RestaurantOwner
)