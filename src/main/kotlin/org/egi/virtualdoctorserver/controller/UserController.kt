package org.egi.virtualdoctorserver.controller

import org.egi.virtualdoctorserver.dto.UserDTO
import org.egi.virtualdoctorserver.exceptions.ConflictException
import org.egi.virtualdoctorserver.exceptions.NotFoundException
import org.egi.virtualdoctorserver.model.NutritionPlan
import org.egi.virtualdoctorserver.model.NutritionValues
import org.egi.virtualdoctorserver.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {


    @GetMapping
    fun getAllUsers() = userService.getAll()

    @PostMapping("")
    fun createUser(@RequestBody user: UserDTO): ResponseEntity<UserDTO> {
        return try {
            val createdUser = userService.createUser(user)
            ResponseEntity(createdUser, HttpStatus.CREATED)
        } catch (e: ConflictException) {
            throw ResponseStatusException(HttpStatus.CONFLICT, e.message)
        }
    }

    @GetMapping("/{username}")
    fun getUser(@PathVariable("username") username: Long): ResponseEntity<UserDTO> {
        return try {
            val user = userService.get(username)
            return if (user != null)
                ResponseEntity(user, HttpStatus.OK)
             else
                ResponseEntity(HttpStatus.NOT_FOUND)

        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("find")
    fun getUserByEmail(@RequestParam email: String): ResponseEntity<UserDTO> {
        return try {
            val user = userService.get(email)
            return if (user != null)
                ResponseEntity(user, HttpStatus.OK)
             else
                ResponseEntity(HttpStatus.NOT_FOUND)

        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/{username}/stats/today")
    fun getDailyStats(@PathVariable("username") username: String): ResponseEntity<NutritionValues> {
        try{
            val stats = userService.getDailyStats(username)
            return ResponseEntity(stats, HttpStatus.OK)
        }
        catch (e: Exception){
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)

        }
    }

    @GetMapping("/{id}/stats/month")
    fun getMonthlyStats(@PathVariable("id") username: String): ResponseEntity<NutritionValues> {
        try{
            val stats = userService.getMonthlyStats(username)
            return ResponseEntity(stats, HttpStatus.OK)
        }
        catch (e: Exception){
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/{id}/addItem/{itemId}")
    fun addUserItem(@PathVariable("id") username: String, @PathVariable("itemId") itemId: Long): ResponseEntity<NutritionValues> {
        println("POST: Adding item $itemId to user $username's list of consumed items.")
        try {
            val stats = userService.addUserItem(username, itemId)
            return ResponseEntity(stats, HttpStatus.OK)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @PutMapping
    fun updateUser(@RequestBody user: UserDTO): ResponseEntity<UserDTO> {
        return try {
            val updatedUser = userService.update(user)
            ResponseEntity(updatedUser, HttpStatus.OK)
        } catch(_: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (_: UnsupportedOperationException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must be provided.")
        }
    }

    @PutMapping("/{username}/customNutritionPlan")
    fun updateNutritionPlanToCustom(@PathVariable("username") username: String, @RequestBody nutritionPlan: NutritionPlan): ResponseEntity<UserDTO> {
        return try {
            val updatedUser = userService.updateNutritionGoalToCustom(username, nutritionPlan)
            ResponseEntity(updatedUser, HttpStatus.OK)
        } catch(_: NotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist.")
        }
    }
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") userId: Long): ResponseEntity<Unit> {
        return try {
            userService.delete(userId)
            ResponseEntity(HttpStatus.OK)
        } catch(_: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (_: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @DeleteMapping("clear/key = {key}")
    fun clearUsers(@PathVariable("key") key: String): ResponseEntity<Unit> {
        return try {
            userService.deleteAll(key)
            ResponseEntity(HttpStatus.OK)
        } catch (_: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (_: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }



}