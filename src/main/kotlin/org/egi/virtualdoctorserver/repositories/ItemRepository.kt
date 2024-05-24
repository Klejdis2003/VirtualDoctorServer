package org.egi.virtualdoctorserver.repositories

import org.egi.virtualdoctorserver.model.Ingredient
import org.egi.virtualdoctorserver.model.Item
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository


interface ItemRepository : CrudRepository<Item, Long> {
    /**
     * Retrieves the menu for a given restaurant
     * @param restaurantId the id of the restaurant
     * @return a list of items that belong to the restaurant with the given id
     */
    fun getByRestaurantId(restaurantId: Long): List<Item>


    @Query(value = """
        SELECT I FROM Item I
        WHERE LOWER(:nutritionTypeName) = ANY (SELECT LOWER(N.name) FROM I.nutritionTypes N) """)
    fun findByNutritionTypeName(nutritionTypeName: String): List<Item>


    @Query(value = """
        SELECT I FROM Item I
        WHERE LOWER(:nutritionTypeName) = ANY (SELECT LOWER(N.name) FROM I.nutritionTypes N)
        AND I.restaurant.id = :restaurantId """)
    fun findByNutritionTypeNameAndRestaurant(nutritionTypeName: String, restaurantId: Long): List<Item>


    fun findByPriceLessThanEqual(price: Float): List<Item>
}

interface IngredientRepository : CrudRepository<Ingredient, Long> {}
