package org.egi.virtualdoctorserver.persistence

import org.egi.virtualdoctorserver.model.Restaurant
import org.egi.virtualdoctorserver.model.RestaurantOwner
import org.egi.virtualdoctorserver.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, Long> {

    fun findByUsername(username: String): Optional<User>


    //@Query("Select new " + "org.egi.virtualdoctorserver.dto.UserDietaryRequirementsDTO(u.calorieLimit, u.maxSugarContent,u.maxFatContent, u.maxProteinContent, u.isVegetarian, u.isVegan) from User u where username = :username")
    @Query("select calorie_limit, max_sugar_content, max_fat_content, max_protein_content, is_vegetarian, is_vegan from users where username = :username", nativeQuery = true)
    fun findDietaryRequirements(username: String): List<Array<Any>>
}

interface RestaurantRepository : CrudRepository<Restaurant, Long> {
    fun findByName(name: String): List<Restaurant>
    fun findByCity(city: String): List<Restaurant>
    fun findByOwnerId(ownerId: Long): List<Restaurant>
}

interface RestaurantOwnerRepository : CrudRepository<RestaurantOwner, Long> {
    fun findByUsername(username: String): Optional<RestaurantOwner>
    fun findByEmail(email: String): Optional<RestaurantOwner>
}
