package org.egi.virtualdoctorserver.model

import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "item")
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Float,
    val calories: Int,
    val sugarContent: Int,
    val fatContent: Int,
    val proteinContent: Int,
    val isVegetarian: Boolean,
    val isVegan: Boolean,

    @Enumerated(EnumType.STRING)
    val itemType: ItemType
)


enum class ItemType {
    DRINK, MAIN, DESSERT, SNACK
}

