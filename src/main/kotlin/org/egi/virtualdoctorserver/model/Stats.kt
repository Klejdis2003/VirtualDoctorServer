package org.egi.virtualdoctorserver.model

class Stats private constructor(
    val calories: Int,
    val carbohydrates: Int,
    val protein: Int,
    val fat: Int,
) {
    companion object {
        fun buildFromUserItemList(list: List<UserItem>): Stats {
//            val calories = list.sumOf { it.item.calories }
//            val protein = list.sumOf { it.item.proteinContent }
//            val fat = list.sumOf { it.item.fatContent }
//            val carbohydrates = list.sumOf { it.item.carbohydrates }
//            return Stats(calories, carbohydrates, protein, fat)
            TODO()
        }
    }

}