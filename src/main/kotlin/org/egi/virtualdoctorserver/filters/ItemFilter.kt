package org.egi.virtualdoctorserver.filters

import org.egi.virtualdoctorserver.model.Item

/**
 * A class that provides a method to filter items using different filter types. Use the [filter] function
 * to filter a list of items based on the filters provided.
 * @see Type
 * @see filter
 */
class ItemFilter: GenericFilter<Item>() {

    override val filterTypes = listOf(
        Type.NutritionType,
        Type.RestaurantId,
        Type.LowCalories,
        Type.HighProtein,
        Type.MinPrice,
        Type.MaxPrice,
        Type.Healthy
    )

    sealed class Type(filterName: String) : GenericFilter.Type<Item>(filterName) {

        data object NutritionType: Type("nutritionType") {
            override fun filter(items: List<Item>, filterValue: String): List<Item> {
                return items.filter { item -> item.nutritionTypes.any { it.name.equals(filterValue, ignoreCase = true) } }
            }
        }

        data object RestaurantId: Type("restaurantId") {
            override fun filter(items: List<Item>, filterValue: String): List<Item> {
                val restaurantId = filterValue.toLong()
                return items.filter { it.restaurant.id == restaurantId }
            }
        }

        data object MinPrice: Type("minPrice") {
            override fun filter(items: List<Item>, filterValue: String): List<Item> {
                val price = filterValue.toFloat()
                return items.filter { it.price >= price }
            }
        }

        data object MaxPrice: Type("maxPrice") {
            override fun filter(items: List<Item>, filterValue: String): List<Item> {
                val price = filterValue.toFloat()
                return items.filter { it.price <= price }
            }
        }

        data object LowCalories: Type("isLowCalories") {
            override fun filter(items: List<Item>, filterValue: String): List<Item> {
                val isLowCalories = filterValue.toBoolean()
                if(isLowCalories)
                    return items.filter { it.nutritionValues.calories < 500 }
                return items
            }
        }

        data object HighProtein: Type("isHighProtein") {
            override fun filter(items: List<Item>, filterValue: String): List<Item> {
                val isHighProtein = filterValue.toBoolean()
                if(isHighProtein)
                    return items.filter { it.nutritionValues.protein >= 25 }
                return items
            }
        }

        data object Healthy: Type("isHealthy") {
            override fun filter(items: List<Item>, filterValue: String): List<Item> {
                val isHealthy = filterValue.toBoolean()
                if(isHealthy)
                    return items.filter {
                        it.nutritionValues.calories <= 500 &&
                        it.nutritionValues.protein >= 20 &&
                        it.nutritionValues.fat <= 20 &&
                        it.nutritionValues.carbohydrates <= 50
                    }
                return items
            }
        }
    }
}




