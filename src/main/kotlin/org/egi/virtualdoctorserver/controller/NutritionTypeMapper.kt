package org.egi.virtualdoctorserver.controller

import org.egi.virtualdoctorserver.dto.NutritionTypeDTO
import org.egi.virtualdoctorserver.model.NutritionType
import org.egi.virtualdoctorserver.repositories.NutritionTypeRepository

class NutritionTypeMapper(private val nutritionTypeRepository: NutritionTypeRepository) {
    fun toNutritionTypeDTO(nutritionType: NutritionType): NutritionTypeDTO {
        return NutritionTypeDTO(
            id = nutritionType.id,
            name = nutritionType.name
        )
    }

    fun toNutritionType(nutritionTypeDTO: NutritionTypeDTO): NutritionType {
        val actualVal = nutritionTypeRepository.findById(nutritionTypeDTO.id)
        return actualVal.get()
    }
}