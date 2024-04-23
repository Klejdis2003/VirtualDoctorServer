package org.egi.virtualdoctorserver.dto

import org.egi.virtualdoctorserver.model.ItemType

data class ItemDTO(
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
    val itemType: ItemType
)