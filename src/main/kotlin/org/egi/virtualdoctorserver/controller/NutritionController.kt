package org.egi.virtualdoctorserver.controller

import org.egi.virtualdoctorserver.model.NutritionPlan
import org.egi.virtualdoctorserver.model.NutritionType
import org.egi.virtualdoctorserver.repositories.NutritionPlanRepository
import org.egi.virtualdoctorserver.repositories.NutritionTypeRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/nutrition")
class NutritionController(
    private val nutritionTypeRepository: NutritionTypeRepository,
    private val nutritionPlanRepository: NutritionPlanRepository
) {
    @GetMapping("/types")
    fun getAllNutritionTypes(): ResponseEntity<List<NutritionType>> {
        println("GET /nutrition/types")
        return ResponseEntity.ok(nutritionTypeRepository.findAll().toList())
    }

    @GetMapping("/plans")
    fun getAllNutritionPlans(): ResponseEntity<List<NutritionPlan>> {
        println("GET /nutrition/plans")
        return ResponseEntity.ok(nutritionPlanRepository.findAll().toList())
    }
}