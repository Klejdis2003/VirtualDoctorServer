package org.egi.virtualdoctorserver.mappers

import org.egi.virtualdoctorserver.controller.NutritionTypeMapper
import org.egi.virtualdoctorserver.dto.UserDTO
import org.egi.virtualdoctorserver.model.User


class UserMapper(
    private val nutritionTypeMapper: NutritionTypeMapper) {

    fun toUserDTO(user: User): UserDTO {
        return UserDTO(
            email = user.email,
            username = user.username,
            age = user.age,
            height = user.height,
            weight = user.weight,
            nutritionPlan = user.nutritionPlan,
            nutritionType = nutritionTypeMapper.toNutritionTypeDTO(user.nutritionType)
        )
    }

    fun toUser(userDTO: UserDTO): User {
        return User(
            email = userDTO.email,
            username = userDTO.username,
            age = userDTO.age,
            height = userDTO.height,
            weight = userDTO.weight,
            nutritionPlan = userDTO.nutritionPlan,
            nutritionType = nutritionTypeMapper.toNutritionType(userDTO.nutritionType)
        )
    }
}