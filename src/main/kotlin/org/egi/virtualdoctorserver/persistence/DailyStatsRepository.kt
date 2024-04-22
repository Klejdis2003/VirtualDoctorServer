package org.egi.virtualdoctorserver.persistence

import org.egi.virtualdoctorserver.model.DailyStats
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface DailyStatsRepository : CrudRepository<DailyStats, Long> {
    @Query("SELECT ds FROM DailyStats ds WHERE ds.user.id = :userId AND DAY(ds.date) = DAY(CURRENT_DATE) AND MONTH(ds.date) = MONTH(CURRENT_DATE) AND YEAR(ds.date) = YEAR(CURRENT_DATE)")
    fun getDailyStatsByUserId(userId: Long): DailyStats

    @Query("SELECT ds FROM DailyStats ds WHERE ds.user.id = :userId AND ds.date BETWEEN :startDate AND :endDate")
    fun getDailyStatsByUserIdAndDateRange(userId: Long, startDate: String, endDate: String): List<DailyStats>

    @Query("SELECT ds FROM DailyStats ds WHERE ds.user.id = :userId AND MONTH(ds.date) = MONTH(CURRENT_DATE) AND YEAR(ds.date) = YEAR(CURRENT_DATE)")
    fun getMonthlyStatsByUserId(userId: Long): List<DailyStats>

}