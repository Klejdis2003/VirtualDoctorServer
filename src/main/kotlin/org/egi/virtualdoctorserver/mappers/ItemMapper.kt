package org.egi.virtualdoctorserver.mappers

import jakarta.persistence.Id
import org.egi.virtualdoctorserver.dto.ItemDTO
import org.egi.virtualdoctorserver.model.Item
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
            calories = item.calories,
            sugarContent = item.sugarContent,
            fatContent = item.fatContent,
            proteinContent = item.proteinContent,
            isVegetarian = item.isVegetarian,
            isVegan = item.isVegan,
            itemType = item.itemType,
        )
    }

    fun toEntity(itemDTO: ItemDTO, restaurant: Restaurant): Item{
        return Item(
            id = -1,
            name = itemDTO.name,
            description = itemDTO.description,
            imageUrl = itemDTO.imageUrl,
            price = itemDTO.price,
            calories = itemDTO.calories,
            sugarContent = itemDTO.sugarContent,
            fatContent = itemDTO.fatContent,
            proteinContent = itemDTO.proteinContent,
            isVegetarian = itemDTO.isVegetarian,
            isVegan = itemDTO.isVegan,
            itemType = itemDTO.itemType,
            restaurant = restaurant
        )
    }
}