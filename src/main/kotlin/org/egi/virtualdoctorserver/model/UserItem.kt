package org.egi.virtualdoctorserver.model

import jakarta.annotation.Nonnull
import jakarta.persistence.*
import java.sql.Date
import java.time.LocalDate

@Entity
@Table(name = "user_item")
data class UserItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    val user: User,

    @JoinColumn(name = "item_id", nullable = false)
    @ManyToOne
    val item: Item,

    @Nonnull
    val date: LocalDate = LocalDate.now()
)
