package org.egi.virtualdoctorserver.mappers

import org.egi.virtualdoctorserver.dto.DailyStatsDTO
import org.egi.virtualdoctorserver.model.DailyStats
import org.springframework.stereotype.Component

@Component
class DailyStatsMapper {
    fun toDTO(dailyStats: DailyStats): DailyStatsDTO {
        return DailyStatsDTO(
            dailyStats.id,
            dailyStats.calories,
            dailyStats.carbohydrates,
            dailyStats.protein,
            dailyStats.fat,
            dailyStats.date
        )

    }
}