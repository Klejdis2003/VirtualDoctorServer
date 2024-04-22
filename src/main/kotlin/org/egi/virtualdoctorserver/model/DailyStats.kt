package org.egi.virtualdoctorserver.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(
    name = "daily_stats",
    uniqueConstraints = [UniqueConstraint(columnNames = ["date", "user_id"])]
)
data class DailyStats(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val calories: Int,
    val carbohydrates: Int,
    val protein: Int,
    val fat: Int,
    val date: Date,
    @JoinColumn(name = "user_id")
    @ManyToOne
    val user: User
)