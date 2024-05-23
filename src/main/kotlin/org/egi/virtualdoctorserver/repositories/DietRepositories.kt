package org.egi.virtualdoctorserver.repositories

import org.egi.virtualdoctorserver.model.NutritionType
import org.springframework.data.repository.CrudRepository

interface NutritionTypeRepository : CrudRepository<NutritionType, Long>
