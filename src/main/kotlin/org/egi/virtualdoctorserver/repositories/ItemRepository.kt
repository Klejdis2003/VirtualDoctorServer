package org.egi.virtualdoctorserver.repositories

import org.egi.virtualdoctorserver.model.Ingredient
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


}

interface IngredientRepository : CrudRepository<Ingredient, Long> {}

