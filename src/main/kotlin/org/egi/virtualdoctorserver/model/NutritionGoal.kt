package org.egi.virtualdoctorserver.model

import jakarta.persistence.*

@Entity
@Table(name = "nutrition_goals")
data class NutritionGoal(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String = "Custom",
    val description: String,
    @OneToOne
    val nutritionValues: NutritionValues,
): ModelTemplate
{
    override fun validate() {
        require(description.isNotBlank()) { "Description cannot be blank" }
        nutritionValues.validate()
    }
}