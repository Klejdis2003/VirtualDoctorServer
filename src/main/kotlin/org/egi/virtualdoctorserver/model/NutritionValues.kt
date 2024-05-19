package org.egi.virtualdoctorserver.model

import jakarta.persistence.*

@Entity
@Table(name = "nutrition_values")
data class NutritionValues(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val calories: Int = NO_LIMIT,
    val fat: Int = NO_LIMIT,
    val protein: Int = NO_LIMIT,
    val carbohydrates: Int = NO_LIMIT,
): ModelTemplate
{
    override fun validate() {
        require(calories >= 0) { "Calories cannot be negative" }
        require(fat >= 0) { "Fat cannot be negative" }
        require(protein >= 0) { "Protein cannot be negative" }
        require(carbohydrates >= 0) { "Carbohydrates cannot be negative" }
    }
}