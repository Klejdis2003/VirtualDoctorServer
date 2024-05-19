package org.egi.virtualdoctorserver.repositories

import org.egi.virtualdoctorserver.model.NutritionType
import org.springframework.data.repository.CrudRepository

interface DietTypeRepository : CrudRepository<NutritionType, Long> {

}