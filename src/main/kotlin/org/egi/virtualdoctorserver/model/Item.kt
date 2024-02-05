package org.egi.virtualdoctorserver.model

import jakarta.persistence.*

@Entity
@Table(name = "item")
data class Item(
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val description: String,
    val price: Float,
    val calories: Int,
    val sugarContent: Int,
    val fatContent: Int,
    val proteinContent: Int,
    val isVegetarian: Boolean,
    val isVegan: Boolean,
    @ElementCollection(targetClass = ItemType::class)
    @CollectionTable(name = "item_type", joinColumns = [JoinColumn(name = "item_id")])
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val type: List<ItemType>,
//    val isGlutenFree: Boolean,
//    val isNutFree: Boolean,
//    val isDairyFree: Boolean,
//    val isHalal: Boolean,
//    val isKosher: Boolean,
//    val restaurant: Restaurant
//
)

enum class ItemType {
    DRINK, MAIN, DESSERT, SNACK
}

