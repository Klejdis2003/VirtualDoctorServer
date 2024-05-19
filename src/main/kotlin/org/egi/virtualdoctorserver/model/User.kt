package org.egi.virtualdoctorserver.model

import jakarta.persistence.*;

const val NO_LIMIT = Integer.MAX_VALUE
@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(unique = true)
    val email: String,
    @Column(unique = true)
    val username: String,

    val age: Int,
    val height: Float, //height in cm
    val weight: Float, //weight in kg

    @JoinColumn(name = "nutrition_goal_id", nullable = false)
    @OneToOne(cascade = [CascadeType.ALL])
    val nutritionGoal: NutritionGoal
) : ModelTemplate {
    override fun validate() {
        require(age >= 10) { "User's age cannot be lower than 10." }
        require(height >= 0) { "Height cannot be negative" }
        require(weight >= 0) { "Weight cannot be negative" }
        nutritionGoal.validate()
    }
}