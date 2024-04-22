package org.egi.virtualdoctorserver.dto

import java.util.Date

data class DailyStatsDTO(
    val id: Long,
    val calories: Int,
    val carbohydrates: Int,
    val protein: Int,
    val fat: Int,
    val date: Date
)