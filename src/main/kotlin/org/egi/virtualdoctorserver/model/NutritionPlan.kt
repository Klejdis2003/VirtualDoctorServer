package org.egi.virtualdoctorserver.model

import jakarta.persistence.*

@Entity
@Table(name = "nutrition_plans")
data class NutritionPlan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String = "No Plan",
    val description: String = "",
    @OneToOne(cascade = [CascadeType.ALL])
    val nutritionValues: NutritionGoalValues = NutritionGoalValues(),
    @Enumerated(EnumType.STRING)
    val type: NutritionPlanType = NutritionPlanType.CUSTOM
): ModelTemplate
{
    init {
        validate()
    }
    override fun validate() {
        nutritionValues.validate()
    }

    companion object {
        val PLANS =
            listOf(
                NutritionPlan(
                    name = "Weight Loss",
                    description = "A diet plan that helps you lose weight",
                    nutritionValues = NutritionGoalValues(
                        protein = 100,
                        fat = 20,
                        carbohydrates = 30,
                        calories = 1500
                    ),
                    type = NutritionPlanType.PREDEFINED
                ),
                NutritionPlan(
                    name = "Weight Gain",
                    description = "A diet plan that helps you gain weight",
                    nutritionValues = NutritionGoalValues(
                        protein = 150,
                        fat = 30,
                        carbohydrates = 50,
                        calories = 2500,
                    ),
                    type = NutritionPlanType.PREDEFINED
                ),
                NutritionPlan(
                    name = "Maintenance",
                    description = "A diet plan that helps you maintain your current weight",
                    nutritionValues = NutritionGoalValues(
                        protein = 120,
                        fat = 25,
                        carbohydrates = 40,
                        calories = 2000
                    ),
                    type = NutritionPlanType.PREDEFINED
                ),
                NutritionPlan(
                    name = "Muscle Gain",
                    description = "A diet plan that helps you grow your muscles",
                    nutritionValues = NutritionGoalValues(
                        protein = 200,
                        fat = 30,
                        carbohydrates = 50,
                        calories = 2500
                    ),
                    type = NutritionPlanType.PREDEFINED
                )
            )

    }
}

enum class NutritionPlanType {
    PREDEFINED,
    CUSTOM
}