package org.egi.virtualdoctorserver.repositories

import org.egi.virtualdoctorserver.model.NutritionGoalValues
import org.egi.virtualdoctorserver.model.NutritionPlan
import org.egi.virtualdoctorserver.model.NutritionPlanType
import org.springframework.data.repository.CrudRepository

interface NutritionPlanRepository : CrudRepository<NutritionPlan, Long> {
    fun findByType(type: NutritionPlanType): List<NutritionPlan>
}

interface NutritionGoalValuesRepository : CrudRepository<NutritionGoalValues, Long> {
    fun save(nutritionValues: NutritionGoalValues): NutritionGoalValues
}