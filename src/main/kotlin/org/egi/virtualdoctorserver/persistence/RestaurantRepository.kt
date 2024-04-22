package org.egi.virtualdoctorserver.persistence

import org.egi.virtualdoctorserver.model.DietaryRestrictions
import org.egi.virtualdoctorserver.model.Restaurant
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

interface RestaurantRepository : CrudRepository<Restaurant, Long>{
    fun findByName(name: String): List<Restaurant>
    fun findByCity(city: String): List<Restaurant>
    fun findByOwnerId(ownerId: Long): List<Restaurant>
}

