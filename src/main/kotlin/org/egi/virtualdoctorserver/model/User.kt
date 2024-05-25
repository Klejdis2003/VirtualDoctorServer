package org.egi.virtualdoctorserver.model

import jakarta.persistence.*


const val NO_LIMIT = Integer.MAX_VALUE
@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(unique = true)
    val username: String,

    val email: String,
    @Column(unique = true)

    val age: Int,
    val height: Int, //height in cm
    val weight: Int, //weight in kg

    @JoinColumn(name = "nutrition_goal_id", nullable = false)
    @ManyToOne
    val nutritionPlan: NutritionPlan,

    @ManyToOne
    @JoinColumn(name = "nutrition_type_id", nullable = false)
    val nutritionType: NutritionType,

    ) : ModelTemplate {
    init {
        validate()
    }
    override fun validate() {
        require(age >= 10) { "User's age cannot be lower than 10." }
        require(height >= 0) { "Height cannot be negative" }
        require(weight >= 0) { "Weight cannot be negative" }
        nutritionPlan.validate()
    }

    companion object {
        val VALUES = listOf(
            User(
                email = "JohnDoe@gmail.com",
                username = "JohnDoe",
                age = 30,
                height = 180,
                weight = 80,
                nutritionPlan = NutritionPlan.PLANS[0],
                nutritionType = NutritionType.VALUES[0],
            )
        )
    }
}