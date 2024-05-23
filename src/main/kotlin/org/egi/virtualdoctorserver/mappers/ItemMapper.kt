package org.egi.virtualdoctorserver.mappers

import org.egi.virtualdoctorserver.dto.ItemDTO
import org.egi.virtualdoctorserver.dto.ItemDTOWithRestaurant
import org.egi.virtualdoctorserver.exceptions.NotFoundException
import org.egi.virtualdoctorserver.model.Item
import org.egi.virtualdoctorserver.repositories.ItemRepository
import org.egi.virtualdoctorserver.services.ItemService
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class ItemMapper(
    private val itemRepository: ItemRepository
) {
    private val nutritionValuesMapper = NutritionValuesMapper()

    fun toItemDTO(item: Item): ItemDTO {
        return ItemDTO(
            id = item.id,
            name = item.name,
            description = item.description,
            price = item.price,
            nutritionValues = nutritionValuesMapper.toNutritionValuesDTO(item.nutritionValues),
            ingredients = item.ingredients,
            imageUrl = item.imageUrl,
            type = item.type
        )
    }

    fun toItemDTOWithRestaurant(item: Item): ItemDTOWithRestaurant {
        return ItemDTOWithRestaurant(
            id = item.id,
            name = item.name,
            description = item.description,
            price = item.price,
            nutritionValues = nutritionValuesMapper.toNutritionValuesDTO(item.nutritionValues),
            ingredients = item.ingredients,
            imageUrl = item.imageUrl,
            type = item.type,
            restaurant = item.restaurant
        )
    }

    @Throws(NotFoundException::class)
    fun toItem(itemDTO: ItemDTO): Item {
        val item = itemRepository
            .findById(itemDTO.id)
            .orElseThrow { NotFoundException("Item with id ${itemDTO.id} not found") }
        return item
    }
}