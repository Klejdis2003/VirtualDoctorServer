package org.egi.virtualdoctorserver.model

import jakarta.persistence.*

@Entity
@Table(name = "item",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["name", "restaurant_id", "item_type", "price"])
    ]
)
class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Float,
    @ManyToOne(cascade = [CascadeType.ALL])
    val nutritionValues: NutritionValues,
    @Enumerated(EnumType.STRING)
    val itemType: ItemType,

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne
    val restaurant: Restaurant,

    @ManyToMany
    @JoinTable(
        name = "item_ingredients",
        joinColumns = [JoinColumn(name = "item_id")],
        inverseJoinColumns = [JoinColumn(name = "ingredient_id")]
    )
    val ingredients: List<Ingredient>,

    @ManyToMany
    @JoinTable(
        name = "item_nutrition_types",
        joinColumns = [JoinColumn(name = "item_id")],
        inverseJoinColumns = [JoinColumn(name = "nutrition_type_id")]
    )
    private val _nutritionTypes: MutableList<NutritionType> = mutableListOf(NutritionType.MAP["Omnivore"]!!)


) : ModelTemplate {
    val nutritionTypes: List<NutritionType>
        get() = _nutritionTypes.toList()

    fun addNutritionType(nutritionType: NutritionType) {
        _nutritionTypes.add(nutritionType)
    }

    fun removeNutritionType(nutritionType: NutritionType) {
        _nutritionTypes.remove(nutritionType)
    }


    override fun validate() {
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(description.isNotBlank()) { "Description cannot be blank" }
        require(imageUrl.isNotBlank()) { "Image URL cannot be blank" }
        require(price >= 0) { "Price cannot be negative" }
        nutritionValues.validate()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (imageUrl != other.imageUrl) return false
        if (price != other.price) return false
        if (nutritionValues != other.nutritionValues) return false
        if (itemType != other.itemType) return false
        if (restaurant != other.restaurant) return false
        if (ingredients != other.ingredients) return false
        if (nutritionTypes != other.nutritionTypes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + nutritionValues.hashCode()
        result = 31 * result + itemType.hashCode()
        result = 31 * result + restaurant.hashCode()
        result = 31 * result + ingredients.hashCode()
        result = 31 * result + nutritionTypes.hashCode()
        return result
    }

    companion object {
        val VALUES =
            listOf(
                Item(
                    name = "Cheeseburger",
                    description = "A delicious cheeseburger",
                    imageUrl = "https://www.sargento.com/assets/Uploads/Recipe/Image/burger_0.jpg",
                    price = 5.99f,
                    nutritionValues = NutritionValues(
                        calories = 500,
                        fat = 20,
                        protein = 30,
                        carbohydrates = 50
                    ),
                    itemType = ItemType.FOOD,
                    restaurant = Restaurant.VALUES[0],
                    ingredients = listOf(
                        Ingredient.INGREDIENT_MAP["Beef"]!!,
                        Ingredient.INGREDIENT_MAP["Cheese"]!!,
                        Ingredient.INGREDIENT_MAP["Bun"]!!,
                        Ingredient.INGREDIENT_MAP["Lettuce"]!!,
                        Ingredient.INGREDIENT_MAP["Bacon"]!!
                    )
                )
            )

    }

}


enum class ItemType {
    FOOD, DRINK, DESSERT
}

