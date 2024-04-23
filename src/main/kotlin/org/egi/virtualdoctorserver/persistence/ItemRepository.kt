package org.egi.virtualdoctorserver.persistence

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.egi.virtualdoctorserver.model.DietaryRestrictions
import org.egi.virtualdoctorserver.model.Item
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param


interface ItemRepository : CrudRepository<Item, Long> {
    /**
     * Retrieves the menu for a given restaurant
     * @param restaurantId the id of the restaurant
     * @return a list of items that belong to the restaurant with the given id
     */
    fun getByRestaurantId(restaurantId: Long): List<Item>

    @Query(
        """
        SELECT i FROM Item i
        WHERE i.calories <= :calories
        AND i.proteinContent <= :proteinContent 
        AND i.fatContent <= :fatContent
        AND i.sugarContent <= :sugarContent
        AND i.isVegetarian = :isVegetarian
        AND i.isVegan = :isVegan
        """,
    )
    fun findAllItemsByDietaryRestrictions(
        @Param("calories") calories: Int,
        @Param("proteinContent") proteinContent: Int,
        @Param("fatContent") fatContent: Int,
        @Param("sugarContent") sugarContent: Int,
        @Param("isVegetarian") isVegetarian: Boolean,
        @Param("isVegan") isVegan: Boolean,
    ): List<Item>

}

