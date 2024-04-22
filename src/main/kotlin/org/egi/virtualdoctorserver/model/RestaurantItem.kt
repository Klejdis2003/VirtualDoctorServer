package org.egi.virtualdoctorserver.model

import jakarta.persistence.*

@Entity
@Table(name = "restaurant_item")
data class RestaurantItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne
    val restaurant: Restaurant,

    @JoinColumn(name = "item_id", nullable = false)
    @ManyToOne
    val item: Item
)