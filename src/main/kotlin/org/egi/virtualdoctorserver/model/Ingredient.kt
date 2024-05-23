package org.egi.virtualdoctorserver.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "ingredient", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "type"])])
data class Ingredient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    @Enumerated(EnumType.STRING)
    val type: IngredientType
): ModelTemplate
{
    override fun validate() {
        require(name.isNotBlank()) { "Name cannot be blank" }
    }
    companion object{
        private val all = EnumMap<IngredientType, Set<String>>(IngredientType::class.java).apply {
            put(
                IngredientType.VEGETABLE, setOf(
                    "Broccoli", "Spinach", "Carrot", "Bell Pepper", "Tomato",
                    "Lettuce", "Cucumber", "Zucchini", "Cauliflower", "Kale"
                )
            )
            put(
                IngredientType.FRUIT, setOf(
                    "Apple", "Banana", "Orange", "Strawberry", "Blueberry",
                    "Raspberry", "Blackberry", "Pineapple", "Mango", "Peach"
                )
            )
            put(
                IngredientType.GRAIN, setOf(
                    "Rice", "Quinoa", "Barley", "Oats", "Buckwheat",
                    "Wheat", "Corn", "Rye", "Millet", "Sorghum"
                )
            )
            put(
                IngredientType.DAIRY, setOf(
                    "Egg", "Milk", "Cheese", "Yogurt", "Butter", "Cream",
                    "Ice Cream", "Cottage Cheese", "Sour Cream", "Kefir", "Cream Cheese"
                )
            )
            put(
                IngredientType.NUT, setOf(
                    "Almond", "Peanut", "Cashew", "Walnut", "Pecan",
                    "Pistachio", "Macadamia", "Hazelnut", "Brazil Nut", "Pine Nut"
                )
            )
            put(
                IngredientType.SEED, setOf(
                    "Chia", "Flax", "Hemp", "Pumpkin", "Sunflower",
                    "Sesame", "Poppy", "Safflower", "Mustard"
                )
            )
            put(
                IngredientType.LEGUME, setOf(
                    "Lentil", "Chickpea", "Black Bean", "Kidney Bean", "Pinto Bean",
                    "Navy Bean", "Soybean", "Green Bean", "Pea", "Peanut"
                )
            )
            put(
                IngredientType.RED_MEAT, setOf(
                    "Beef", "Pork", "Lamb", "Venison", "Bison",
                    "Elk", "Moose", "Boar", "Goat", "Horse", "Bacon"
                )
            )
            put(
                IngredientType.POULTRY, setOf(
                    "Chicken", "Turkey", "Duck", "Goose", "Quail",
                    "Pheasant", "Partridge", "Ostrich", "Emu", "Rhea"
                )
            )
            put(
                IngredientType.SEAFOOD, setOf(
                    "Salmon", "Tuna", "Trout", "Mackerel", "Sardine",
                    "Herring", "Anchovy", "Cod", "Haddock", "Pollock"
                )
            )
            put(
                IngredientType.SPICE, setOf(
                    "Salt", "Pepper", "Cinnamon", "Garlic", "Ginger",
                    "Turmeric", "Cumin", "Paprika", "Chili", "Coriander"
                )
            )
            put(
                IngredientType.HERB, setOf(
                    "Basil", "Parsley", "Cilantro", "Mint", "Rosemary",
                    "Thyme", "Sage", "Oregano", "Dill", "Chives"
                )
            )
            put(
                IngredientType.FAT, setOf(
                    "Olive Oil", "Coconut Oil", "Butter", "Lard", "Ghee",
                    "Palm Oil", "Avocado Oil", "Sesame Oil", "Sunflower Oil", "Canola Oil"
                )
            )
            put(
                IngredientType.SWEETENER, setOf(
                    "Sugar", "Honey", "Maple Syrup", "Agave", "Stevia",
                    "Aspartame", "Sucralose", "Saccharin", "Monk Fruit", "Xylitol"
                )
            )
            put(
                IngredientType.BEVERAGE, setOf(
                    "Water", "Coffee", "Tea", "Milk", "Juice",
                    "Soda", "Beer", "Wine", "Liquor", "Kombucha"
                )
            )
            put(
                IngredientType.PROTEIN, setOf(
                    "Whey", "Casein", "Soy", "Pea", "Hemp",
                    "Rice", "Egg", "Beef", "Chicken", "Pork"
                )
            )
            put(
                IngredientType.CAFFEINE, setOf(
                    "Coffee", "Tea", "Energy Drink", "Soda", "Chocolate"
                )
            )
            put(
                IngredientType.GLUTEN, setOf(
                    "Wheat", "Barley", "Rye", "Oats", "Spelt", "Bun",
                    "Kamut", "Triticale", "Farro", "Einkorn", "Durum"
                )
            )
        }

        val VALUES : List<Ingredient> =
            all.flatMap { (type, names) -> names.map { Ingredient(name = it, type = type) } }

        val INGREDIENT_MAP : Map<String, Ingredient> = VALUES.associateBy { it.name }
    }
}


enum class IngredientType {
    VEGETABLE,
    FRUIT,
    GRAIN,
    DAIRY,
    NUT,
    SEED,
    LEGUME,
    RED_MEAT,
    POULTRY,
    SEAFOOD,
    SPICE,
    HERB,
    FAT,
    SWEETENER,
    BEVERAGE,
    PROTEIN,
    CAFFEINE,
    GLUTEN
}
