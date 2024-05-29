package org.egi.virtualdoctorserver.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "item",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["name", "restaurant_id", "item_type", "price"])
    ]
)

/**
 * Represents an item that can be ordered from a restaurant
 * @property id the unique identifier of the item
 * @property name the name of the item
 * @property description a description of the item
 * @property imageUrl a URL to an image of the item (local or online)
 * @property price the price of the item
 * @property nutritionValues the nutritional values of the item
 * @property itemType the type of item (food, drink, dessert)
 * @property restaurant the restaurant that serves the item
 * @property ingredients the ingredients that make up the item
 * @property nutritionTypes the nutrition types that the item falls under
 */
class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Float,

    @OneToOne(cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn
    val nutritionValues: ItemNutritionValues,

    @Enumerated(EnumType.STRING)
    val type: ItemType,

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "item_nutrition_types",
        joinColumns = [JoinColumn(name = "item_id")],
        inverseJoinColumns = [JoinColumn(name = "nutrition_type_id")]
    )
    val nutritionTypes: List<NutritionType> = emptyList()

) : ModelTemplate {

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

        return  name == other.name &&
                description == other.description &&
                imageUrl == other.imageUrl &&
                price == other.price &&
                nutritionValues == other.nutritionValues &&
                type == other.type &&
                restaurant == other.restaurant &&
                ingredients == other.ingredients &&
                nutritionTypes == other.nutritionTypes
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + nutritionValues.hashCode()
        result = 31 * result + type.hashCode()
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
                    nutritionValues = ItemNutritionValues(
                        calories = 500,
                        fat = 20,
                        protein = 30,
                        carbohydrates = 50
                    ),
                    type = ItemType.FOOD,
                    restaurant = Restaurant.VALUES[0],
                    ingredients = listOf(
                        Ingredient.INGREDIENT_MAP["Beef"]!!,
                        Ingredient.INGREDIENT_MAP["Cheese"]!!,
                        Ingredient.INGREDIENT_MAP["Bun"]!!,
                        Ingredient.INGREDIENT_MAP["Lettuce"]!!,
                        Ingredient.INGREDIENT_MAP["Bacon"]!!
                    ),
                    nutritionTypes = listOf(
                        NutritionType.MAP["Omnivore"]!!,
                        NutritionType.MAP["Keto"]!!
                    )
                )
            )

    }

    fun copy(
        name: String = this.name,
        description: String = this.description,
        imageUrl: String = this.imageUrl,
        price: Float = this.price,
        nutritionValues: ItemNutritionValues = this.nutritionValues,
        type: ItemType = this.type,
        restaurant: Restaurant = this.restaurant,
        ingredients: List<Ingredient> = this.ingredients,
        nutritionTypes: List<NutritionType> = this.nutritionTypes
    ) = Item(
        name = name,
        description = description,
        imageUrl = imageUrl,
        price = price,
        nutritionValues = nutritionValues,
        type = type,
        restaurant = restaurant,
        ingredients = ingredients,
        nutritionTypes = nutritionTypes

    )


}


enum class ItemType {
    FOOD, DRINK, DESSERT
}



