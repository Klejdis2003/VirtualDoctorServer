package org.egi.virtualdoctorserver.services

import org.egi.virtualdoctorserver.dto.DailyStatsDTO
import org.egi.virtualdoctorserver.mappers.DailyStatsMapper
import org.egi.virtualdoctorserver.persistence.DailyStatsRepository
import org.springframework.stereotype.Service

@Service
class DailyStatsService(
    private val dailyStatsRepository: DailyStatsRepository,
    private val dailyStatsMapper: DailyStatsMapper
) {
    fun getDailyStatsByUserId(userId: Long): DailyStatsDTO {
        val dailyStats = this.dailyStatsRepository.getDailyStatsByUserId(userId)
        return this.dailyStatsMapper.toDTO(dailyStats)
    }

}