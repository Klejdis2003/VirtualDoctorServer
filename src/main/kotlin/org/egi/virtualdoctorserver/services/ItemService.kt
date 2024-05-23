package org.egi.virtualdoctorserver.services

import org.egi.virtualdoctorserver.dto.ItemDTOWithRestaurant
import org.egi.virtualdoctorserver.exceptions.NoPermissionException
import org.egi.virtualdoctorserver.mappers.ItemMapper
import org.egi.virtualdoctorserver.model.*
import org.egi.virtualdoctorserver.repositories.ItemRepository
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class ItemService(
    private val itemRepository: ItemRepository,
    private val itemMapper: ItemMapper
) {
    fun getAll(): List<ItemDTOWithRestaurant> =
        itemRepository.findAll().map { itemMapper.toItemDTOWithRestaurant(it) }


    fun get(id: Long): ItemDTOWithRestaurant? {
        return itemRepository.findById(id).map { itemMapper.toItemDTOWithRestaurant(it) }.orElse(null)
    }

    fun create(item: Item) = itemRepository.save(item)

    fun delete(id: Long) = itemRepository.deleteById(id)

    @Throws(NoPermissionException::class)
    fun deleteAll(key: String) {
        if(key == System.getenv("ADMIN_KEY")) {
            itemRepository.deleteAll()
            println("Items table cleared.")
        }
        throw NoPermissionException("Invalid key provided. No permission to delete all items.")
    }

    fun filterByUserPreferences(user: User): List<Item> {
        return filter(user.nutritionPlan.nutritionValues, user.nutritionType)
    }

    fun filter(nutritionValues: NutritionValues, nutritionType: NutritionType): List<Item> {
        val items = itemRepository.findAll().toList()
        val filteredByNutritionValues = filterByNutritionValues(nutritionValues)
        val filteredByNutritionType = filterByNutritionType(nutritionType)
        return filteredByNutritionType.intersect(filteredByNutritionValues.toSet()).toList()
    }

    fun filterByNutritionGoal(nutritionPlan: NutritionPlan): List<Item> {
       return filterByNutritionValues(nutritionPlan.nutritionValues)
    }

    fun filterByNutritionValues(nutritionValues: NutritionValues): List<Item> {
        val items = itemRepository.findAll().toList()
        return items.filter{
                it.nutritionValues.protein <= nutritionValues.protein &&
                it.nutritionValues.fat <= nutritionValues.fat &&
                it.nutritionValues.carbohydrates <= nutritionValues.carbohydrates &&
                it.nutritionValues.calories <= nutritionValues.calories
            }
    }

    fun filterByNutritionType(nutritionType: NutritionType): List<Item> {
        val items = itemRepository.findAll().toList()
        return items.filter { it.nutritionTypes.contains(nutritionType) }
    }

    fun getHighProteinItems(): List<ItemDTOWithRestaurant> {
        val items = itemRepository.findAll().toList()
        return items
            .filter { it.nutritionValues.protein >= 20 }
            .map { itemMapper.toItemDTOWithRestaurant(it) }
    }

    fun getHighCarbItems(): List<ItemDTOWithRestaurant> {
        val items = itemRepository.findAll().toList()
        return items
            .filter { it.nutritionValues.carbohydrates >= 50 }
            .map { itemMapper.toItemDTOWithRestaurant(it) }
    }

    fun getLowCalorieItems(): List<ItemDTOWithRestaurant> {
        val items = itemRepository.findAll().toList()
        return items
            .filter { it.nutritionValues.calories <= 500 }
            .map { itemMapper.toItemDTOWithRestaurant(it) }
    }

    fun getHealthyItems(): List<ItemDTOWithRestaurant> {
        val items = itemRepository.findAll().toList()
        return items
            .filter {
                it.nutritionValues.calories <= 500 &&
                it.nutritionValues.protein >= 20 &&
                it.nutritionValues.fat <= 20 &&
                it.nutritionValues.carbohydrates <= 50
            }
            .map { itemMapper.toItemDTOWithRestaurant(it) }
    }

    fun update(item: Item): Item {
        return itemRepository.save(item)
    }

}