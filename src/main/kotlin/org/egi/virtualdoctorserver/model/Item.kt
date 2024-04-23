package org.egi.virtualdoctorserver.model

import jakarta.persistence.*
import org.hibernate.annotations.Type

@Entity
@Table(name = "item",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["name", "restaurant_id", "item_type", "price", "calories", "sugar_content", "fat_content", "protein_content", "is_vegetarian", "is_vegan"])
    ]
)
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
    val itemType: ItemType,

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne
    val restaurant: Restaurant
)


enum class ItemType {
    FOOD, DRINK, DESSERT
}

