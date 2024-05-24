package org.egi.virtualdoctorserver.dto

import org.egi.virtualdoctorserver.model.RestaurantOwner

data class RestaurantNoMenuDTO(
    val id: Long,
    val name: String,
    val streetAddress: String,
    val city: String,
    val postcode: String,
    val country: String,
    val telephone: String,
    val email: String,
    val website: String,
)

data class RestaurantDTO(
    val id: Long,
    val name: String,
    val streetAddress: String,
    val city: String,
    val postcode: String,
    val country: String,
    val telephone: String,
    val email: String,
    val website: String,
    val menu: List<ItemDTO>
)