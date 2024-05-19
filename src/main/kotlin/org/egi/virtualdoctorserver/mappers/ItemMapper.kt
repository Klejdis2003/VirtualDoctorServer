package org.egi.virtualdoctorserver.mappers

import org.egi.virtualdoctorserver.dto.ItemDTO
import org.egi.virtualdoctorserver.model.Item
import org.egi.virtualdoctorserver.model.NutritionValues
import org.egi.virtualdoctorserver.model.Restaurant
import org.springframework.stereotype.Component

@Component
class ItemMapper {
    fun toDTO(item: Item): ItemDTO {
        return ItemDTO(
            id = item.id,
            name = item.name,
            description = item.description,
            imageUrl = item.imageUrl,
            price = item.price,
            itemType = item.itemType
        )
    }

    fun toEntity(itemDTO: ItemDTO, restaurant: Restaurant): Item{
        return Item(
            id = -1,
            name = itemDTO.name,
            description = itemDTO.description,
            imageUrl = itemDTO.imageUrl,
            price = itemDTO.price,
            itemType = itemDTO.itemType,
            restaurant = restaurant,
            ingredients = emptyList(),
            nutritionValues = NutritionValues(0, 0, 0, 0, 0)
        )
    }
}