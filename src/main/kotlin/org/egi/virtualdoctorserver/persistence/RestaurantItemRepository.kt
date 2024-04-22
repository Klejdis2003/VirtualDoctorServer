package org.egi.virtualdoctorserver.persistence

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.egi.virtualdoctorserver.model.DietaryRestrictions
import org.egi.virtualdoctorserver.model.Item
import org.egi.virtualdoctorserver.model.RestaurantItem
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


interface RestaurantItemRepositoryCustom{
    /**
     * Retrieves the items for a specific restaurant that meet the dietary restrictions of the user.
     * @param restaurantId the id of the restaurant
     * @param dietaryRestrictions the dietary restrictions of the user
     * @return the items that meet the dietary restrictions of the user
     */
    fun filterItemsByDietaryRestrictions(restaurantId: Long, dietaryRestrictions: DietaryRestrictions): List<Item>

    /**
     * Retrieves all the items that fit the user dietary restrictions together with restaurant information
     * @param dietaryRestrictions the dietary restrictions of the user
     * @return the items for the restaurant
     */
    fun findAllItemsByDietaryRestrictions(dietaryRestrictions: DietaryRestrictions): List<RestaurantItem>

    fun getRestaurantItemsByRestaurantId(restaurantId: Long): List<Item>
}
interface RestaurantItemRepository : CrudRepository<RestaurantItem, Long>, RestaurantItemRepositoryCustom {

}

@Repository
class RestaurantItemRepositoryImpl: RestaurantItemRepositoryCustom {
    @PersistenceContext
    lateinit var entityManager: EntityManager
    override fun filterItemsByDietaryRestrictions(restaurantId: Long, dietaryRestrictions: DietaryRestrictions): List<Item> {
        val sql = """
            SELECT i.*
            FROM item i
            JOIN restaurant_item ri ON i.id = ri.item_id
            JOIN restaurant r ON ri.restaurant_id = r.id
            WHERE
                (i.is_vegetarian = :isVegetarian OR i.is_vegan = :isVegan)
                AND i.calories <= :calorieLimit
                AND i.sugar_content <= :maxSugarContent
                AND i.fat_content <= :maxFatContent
                AND i.protein_content <= :maxProteinContent
                AND r.id = :restaurantId
        """.trimIndent()
        val items: List<Item> = entityManager.createNativeQuery(sql, Item::class.java)
            .setParameter("isVegetarian", dietaryRestrictions.isVegetarian)
            .setParameter("isVegan", dietaryRestrictions.isVegan)
            .setParameter("calorieLimit", dietaryRestrictions.calorieLimit)
            .setParameter("maxSugarContent", dietaryRestrictions.maxSugarContent)
            .setParameter("maxFatContent", dietaryRestrictions.maxFatContent)
            .setParameter("maxProteinContent", dietaryRestrictions.maxProteinContent)
            .setParameter("restaurantId", restaurantId)
            .resultList as ArrayList<Item>

        return items
    }

    override fun findAllItemsByDietaryRestrictions(dietaryRestrictions: DietaryRestrictions): List<RestaurantItem> {
        val sql = """
            SELECT ri.*
            FROM item i
            JOIN restaurant_item ri ON i.id = ri.item_id
            JOIN restaurant r ON ri.restaurant_id = r.id
            WHERE
                i.is_vegetarian = :isVegetarian
                AND i.is_vegan = :isVegan
                AND i.calories <= :calorieLimit
                AND i.sugar_content <= :maxSugarContent
                AND i.fat_content <= :maxFatContent
                AND i.protein_content <= :maxProteinContent
        """.trimIndent()
        val items: List<RestaurantItem> = entityManager.createNativeQuery(sql, RestaurantItem::class.java)
            .setParameter("isVegetarian", dietaryRestrictions.isVegetarian)
            .setParameter("isVegan", dietaryRestrictions.isVegan)
            .setParameter("calorieLimit", dietaryRestrictions.calorieLimit)
            .setParameter("maxSugarContent", dietaryRestrictions.maxSugarContent)
            .setParameter("maxFatContent", dietaryRestrictions.maxFatContent)
            .setParameter("maxProteinContent", dietaryRestrictions.maxProteinContent)
            .resultList as ArrayList<RestaurantItem>
        return items

    }

    override fun getRestaurantItemsByRestaurantId(restaurantId: Long): List<Item> {
        val sql = """
            SELECT i.*
            FROM item i
            JOIN restaurant_item ri ON i.id = ri.item_id
            WHERE ri.restaurant_id = :restaurantId
            ORDER BY i.name
        """.trimIndent()

        val items: List<Item> = entityManager.createNativeQuery(sql, Item::class.java)
            .setParameter("restaurantId", restaurantId)
            .resultList as ArrayList<Item>

        return items
    }
}


