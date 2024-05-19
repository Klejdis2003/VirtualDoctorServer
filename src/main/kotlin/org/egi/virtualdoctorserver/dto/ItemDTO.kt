package org.egi.virtualdoctorserver.dto

import org.egi.virtualdoctorserver.model.ItemType

data class ItemDTO(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Float,
    val itemType: ItemType
)