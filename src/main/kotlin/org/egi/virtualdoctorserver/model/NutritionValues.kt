package org.egi.virtualdoctorserver.model

import jakarta.persistence.*


@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
abstract class NutritionValues(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val calories: Int,
    val fat: Int,
    val protein: Int,
    val carbohydrates: Int,
): ModelTemplate
{
    init {
        validate()
    }
    final override fun validate() {
        require(calories >= 0) { "Calories cannot be negative" }
        require(fat >= 0) { "Fat cannot be negative" }
        require(protein >= 0) { "Protein cannot be negative" }
        require(carbohydrates >= 0) { "Carbohydrates cannot be negative" }
    }

    override fun equals(other: Any?): Boolean {
        return  other != null &&
                this::class == other::class &&
                id == (other as NutritionValues).id &&
                calories == other.calories &&
                fat == other.fat &&
                protein == other.protein &&
                carbohydrates == other.carbohydrates
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "NutritionValues(id=$id, calories=$calories, fat=$fat, protein=$protein, carbohydrates=$carbohydrates)"
    }
}


@Entity
@Table(name = "nutrition_goal_values")
class NutritionGoalValues(
    id: Long = 0,
    calories: Int = NO_LIMIT,
    fat: Int = NO_LIMIT,
    protein: Int = NO_LIMIT,
    carbohydrates: Int = NO_LIMIT
): NutritionValues(id, calories, fat, protein, carbohydrates) {

    companion object {
        fun buildFromUserItemList(list: List<UserItem>): NutritionGoalValues {
            var calories = 0
            var carbohydrates = 0
            var protein = 0
            var fat = 0
            list.forEach {
                calories += it.item.nutritionValues.calories
                carbohydrates += it.item.nutritionValues.carbohydrates
                protein += it.item.nutritionValues.protein
                fat += it.item.nutritionValues.fat
            }
            return NutritionGoalValues(
                calories = calories,
                carbohydrates = carbohydrates,
                protein = protein,
                fat = fat
            )
        }
    }
}

@Entity
@Table(name = "item_nutrition_values")
class ItemNutritionValues(
    id: Long = 0,
    calories: Int,
    fat: Int,
    protein: Int,
    carbohydrates: Int
): NutritionValues(id, calories, fat, protein, carbohydrates)
