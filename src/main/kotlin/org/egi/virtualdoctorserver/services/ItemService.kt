package org.egi.virtualdoctorserver.services

import org.egi.virtualdoctorserver.dto.ItemDTOWithRestaurant
import org.egi.virtualdoctorserver.exceptions.InvalidQueryParamException
import org.egi.virtualdoctorserver.exceptions.NoPermissionException
import org.egi.virtualdoctorserver.filters.ItemFilter
import org.egi.virtualdoctorserver.mappers.ItemMapper
import org.egi.virtualdoctorserver.model.*
import org.egi.virtualdoctorserver.repositories.IngredientRepository
import org.egi.virtualdoctorserver.repositories.ItemRepository
import org.egi.virtualdoctorserver.repositories.NutritionTypeRepository
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class ItemService(
    private val itemRepository: ItemRepository,
    private val ingredientRepository: IngredientRepository,
    private val itemMapper: ItemMapper, private val nutritionTypeRepository: NutritionTypeRepository
) {
    private val itemFilter = ItemFilter()
    fun getAll(filters: Map<String, String> = emptyMap()): List<ItemDTOWithRestaurant> {
        var items = itemRepository.findAll().toList()
        return try {
            items = itemFilter.filter(items, filters)
            items.map { itemMapper.toItemDTOWithRestaurant(it) }
        } catch (e: NoSuchElementException) {
            throw InvalidQueryParamException("Invalid filter provided. Available filters are: ${itemFilter.filterTypes.map { it.filterName }}")
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }


    fun get(id: Long): ItemDTOWithRestaurant? {
        return itemRepository.findById(id).map { itemMapper.toItemDTOWithRestaurant(it) }.orElse(null)
    }

    fun create(item: Item) {
        val newItem = getItemWithUpdatedNutritionTypes(item)
        itemRepository.save(newItem)
    }

    fun delete(id: Long) = itemRepository.deleteById(id)

    @Throws(NoPermissionException::class)
    fun deleteAll(key: String) {
        if(key == System.getenv("ADMIN_KEY")) {
            itemRepository.deleteAll()
            println("Items table cleared.")
        }
        throw NoPermissionException("Invalid key provided. No permission to delete all items.")
    }


    /**
     * Only returns the items where each of the nutrients is less than or equal to values
     * specified in the nutritionValues parameter.
     * @param nutritionValues The maximum values for each nutrient.
     * @return A list of items that meet the criteria.
     */
    fun filterByNutritionValues(nutritionValues: NutritionValues): List<Item> {
        val items = itemRepository.findAll().toList()
        return items.filter{
                it.nutritionValues.protein <= nutritionValues.protein &&
                it.nutritionValues.fat <= nutritionValues.fat &&
                it.nutritionValues.carbohydrates <= nutritionValues.carbohydrates &&
                it.nutritionValues.calories <= nutritionValues.calories
            }
    }

    private fun getItemWithUpdatedNutritionTypes(item: Item): Item{
        val nutritionTypes = nutritionTypeRepository.findAll()
        val itemIngredientTypes = item.ingredients.map { it.type }.toHashSet()
        val itemNutritionTypes = item.nutritionTypes.toMutableSet()
        nutritionTypes.forEach { nutritionType ->
            if (nutritionType.disallowedIngredientTypes.any { itemIngredientTypes.contains(it) }) {
                itemNutritionTypes.remove(nutritionType)
            }
        }
        val newItem = item.copy(nutritionTypes = itemNutritionTypes.toList())
        return newItem
    }

    fun searchIngredients(query: String): List<Ingredient> {
        return ingredientRepository.searchByNameContainsIgnoreCaseOrderByName(query)
    }


    fun update(item: Item): Item {
        return itemRepository.save(item)
    }

    fun help(): String{
        val html = """
            <html>
            <head>
                <title>Item Service</title>
            </head>
            <body>
                <h1>Item Service</h1>
                <p>This service provides methods to interact with items in the database.</p>
                <h2>Endpoints</h2>
                <ul>
                    <li>GET /items</li>
                    <p>Optional query parameters:</p>
                        <ul>
                            ${
                                itemFilter.filterTypes.joinToString(separator = "") {
                                    "<li>${it.filterName}</li>"
                                }
                            }
                        </ul>
                    <li>GET /items/{id}</li>
                    <li>POST /items</li>
                    <li>DELETE /items/{id}</li>
                    <li>DELETE /items</li>
                </ul>
            
            </body>
            </html>
        """.trimIndent()
        return html
    }
}

