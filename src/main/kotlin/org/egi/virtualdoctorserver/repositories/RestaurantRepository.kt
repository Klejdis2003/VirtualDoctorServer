package org.egi.virtualdoctorserver.repositories

import org.egi.virtualdoctorserver.model.Restaurant
import org.springframework.data.repository.CrudRepository

interface RestaurantRepository : CrudRepository<Restaurant, Long>{
    fun getById(id: Long): Restaurant?
    fun findByCity(city: String): List<Restaurant>
    fun findByOwnerId(ownerId: Long): List<Restaurant>
}

