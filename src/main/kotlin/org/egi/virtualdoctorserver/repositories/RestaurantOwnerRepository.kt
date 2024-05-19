package org.egi.virtualdoctorserver.repositories

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.egi.virtualdoctorserver.model.Restaurant
import org.egi.virtualdoctorserver.model.RestaurantOwner
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

interface RestaurantOwnerRepositoryCustom{
    fun getOwnedRestaurants(email: String): List<Restaurant>
}
interface RestaurantOwnerRepository : CrudRepository<RestaurantOwner, Long>, RestaurantOwnerRepositoryCustom{

    fun findByUsername(username: String): Optional<RestaurantOwner>
    fun findByEmail(email: String): Optional<RestaurantOwner>

}

@Repository
class RestaurantOwnerRepositoryImpl(private val restaurantRepository: RestaurantRepository): RestaurantOwnerRepositoryCustom{
    @PersistenceContext
    lateinit var entityManager: EntityManager
    override fun getOwnedRestaurants(email: String): List<Restaurant>{
        val sql = "select id from restaurant_owner where email = :email"
        val ownerId: Long = entityManager.createNativeQuery(sql)
            .setParameter("email", email)
            .singleResult as Long
        return restaurantRepository.findByOwnerId(ownerId)
    }
}