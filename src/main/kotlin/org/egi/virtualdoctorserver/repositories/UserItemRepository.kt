package org.egi.virtualdoctorserver.repositories

import org.egi.virtualdoctorserver.model.UserItem
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface UserItemRepository : CrudRepository<UserItem, Long> {
    fun getByUserUsername(username: String): List<UserItem>
    fun getByItemId (itemId: Long): List<UserItem>
    fun getByUserUsernameAndDateBetween(username: String, startDate: LocalDate, endDate: LocalDate): List<UserItem>

    /**
     * Add an item to a user's list of consumed items
     * @param userId the id of the user
     * @param itemId the id of the item
     */

    @Modifying
    @Query("INSERT INTO user_item (username, item_id, date) VALUES (:username, :itemId, CURRENT_DATE)", nativeQuery = true)
    fun save(username: String, itemId: Long)
}