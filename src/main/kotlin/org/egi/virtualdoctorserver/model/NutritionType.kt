package org.egi.virtualdoctorserver.model

import jakarta.persistence.*

@Entity
@Table(name = "nutrition_types")
data class NutritionType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String = "Omnivore",
    @Enumerated(EnumType.STRING)
    val disallowedIngredientTypes : Set<IngredientType> = emptySet()
) {
    companion object {
        val VALUES: List<NutritionType> =
            listOf(
                NutritionType(
                    name = "Vegan",
                    disallowedIngredientTypes = setOf(
                        IngredientType.RED_MEAT,
                        IngredientType.POULTRY,
                        IngredientType.SEAFOOD,
                        IngredientType.DAIRY
                    ),
                ),
                NutritionType(
                    name = "Vegetarian",
                    disallowedIngredientTypes = setOf(
                        IngredientType.RED_MEAT,
                        IngredientType.POULTRY,
                        IngredientType.SEAFOOD
                    )
                ),
                NutritionType(
                    name = "Pescatarian",
                    disallowedIngredientTypes = setOf(
                        IngredientType.RED_MEAT,
                        IngredientType.POULTRY
                    )
                ),
                NutritionType(
                    name = "Paleo",
                    disallowedIngredientTypes = setOf(
                        IngredientType.GRAIN,
                        IngredientType.DAIRY,
                        IngredientType.LEGUME
                    )
                ),
                NutritionType(
                    name = "Keto",
                    disallowedIngredientTypes = setOf(
                        IngredientType.GRAIN,
                        IngredientType.SWEETENER,
                        IngredientType.FRUIT
                    )
                ),
                NutritionType()
            )

        val MAP = VALUES.associateBy { it.name }
    }
}