package org.egi.virtualdoctorserver.persistence

import jakarta.transaction.Transactional
import org.egi.virtualdoctorserver.model.UserItem
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface UserItemRepository : CrudRepository<UserItem, Long> {
    fun getByUserId (userId: Long): List<UserItem>
    fun getByItemId (itemId: Long): List<UserItem>
    fun getByUserIdAndDateBetween(userId: Long, startDate: LocalDate, endDate: LocalDate): List<UserItem>

    /**
     * Add an item to a user's list of consumed items
     * @param userId the id of the user
     * @param itemId the id of the item
     */
    @Transactional
    @Modifying
    @Query("""
        INSERT INTO user_item (user_id, item_id, date)
        VALUES (:userId, :itemId, CURRENT_DATE)
        
    """, nativeQuery = true)
    fun save(userId: Long, itemId: Long)
}