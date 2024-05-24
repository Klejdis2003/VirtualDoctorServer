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
    val id: Long = 0,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    val user: User,

    @JoinColumn(name = "item_id", nullable = false)
    @ManyToOne
    val item: Item,

    @Nonnull
    val date: LocalDate = LocalDate.now()
): ModelTemplate {
    init {
        validate()
    }

    override fun validate() {
        require(id >= 0) { "id must be greater than or equal to 0" }
        require(user.id >= 0) { "user id must be greater than or equal to 0" }
        require(item.id >= 0) { "item id must be greater than or equal to 0" }
    }

}

