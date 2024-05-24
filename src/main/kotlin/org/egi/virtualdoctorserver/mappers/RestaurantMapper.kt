package org.egi.virtualdoctorserver.mappers

import org.egi.virtualdoctorserver.dto.ItemDTO
import org.egi.virtualdoctorserver.dto.RestaurantDTO
import org.egi.virtualdoctorserver.dto.RestaurantNoMenuDTO
import org.egi.virtualdoctorserver.model.Restaurant
import org.egi.virtualdoctorserver.model.RestaurantOwner
import java.awt.Menu

class RestaurantMapper(private val itemMapper: ItemMapper) {

    fun toRestaurantNoMenuDTO(restaurant: Restaurant): RestaurantNoMenuDTO {
        return RestaurantNoMenuDTO(
            id = restaurant.id,
            name = restaurant.name,
            streetAddress = restaurant.streetAddress,
            city = restaurant.city,
            postcode = restaurant.postcode,
            country = restaurant.country,
            telephone = restaurant.telephone,
            email = restaurant.email,
            website = restaurant.website
        )
    }

    fun toRestaurantDTO(restaurant: Restaurant, menu: List<ItemDTO>): RestaurantDTO {
        return RestaurantDTO(
            id = restaurant.id,
            name = restaurant.name,
            streetAddress = restaurant.streetAddress,
            city = restaurant.city,
            postcode = restaurant.postcode,
            country = restaurant.country,
            telephone = restaurant.telephone,
            email = restaurant.email,
            website = restaurant.website,
            menu = menu
        )
    }

    fun toRestaurant(restaurantDTO: RestaurantDTO, owner: RestaurantOwner): Restaurant {

        return Restaurant(
            id = restaurantDTO.id,
            name = restaurantDTO.name,
            streetAddress = restaurantDTO.streetAddress,
            city = restaurantDTO.city,
            postcode = restaurantDTO.postcode,
            country = restaurantDTO.country,
            telephone = restaurantDTO.telephone,
            email = restaurantDTO.email,
            website = restaurantDTO.website,
            owner = owner
        )
    }
}