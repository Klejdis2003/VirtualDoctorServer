package org.egi.virtualdoctorserver.controller

import org.egi.virtualdoctorserver.model.Stats
import org.egi.virtualdoctorserver.model.User
import org.egi.virtualdoctorserver.repositories.UserRepository
import org.egi.virtualdoctorserver.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userRepository: UserRepository,  private val userService: UserService) {
    @GetMapping
    fun getAllUsers() = userRepository.findAll().toList()

    @PostMapping("")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        if(userRepository.findByUsername(user.username).isPresent){
            return ResponseEntity(HttpStatus.CONFLICT)
        }
        val createdUser = userService.saveUser(user)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") userId: Int): ResponseEntity<User> {
        val user = userRepository.findById(userId.toLong())
        return if (user.isPresent) {
            ResponseEntity(user.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("find")
    fun getUserByEmail(@RequestParam email: String): ResponseEntity<User> {
        val user = userRepository.findByEmail(email)
        return if (user.isPresent) {
            ResponseEntity(user.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/{id}/stats/today")
    fun getDailyStats(@PathVariable("id") userId: Long): ResponseEntity<Stats> {
        try{
            val stats = userService.getDailyStats(userId)
            return ResponseEntity(stats, HttpStatus.OK)
        }
        catch (e: Exception){
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)

        }
    }

    @GetMapping("/{id}/stats/month")
    fun getMonthlyStats(@PathVariable("id") userId: Long): ResponseEntity<Stats> {
        try{
            val stats = userService.getMonthlyStats(userId)
            return ResponseEntity(stats, HttpStatus.OK)
        }
        catch (e: Exception){
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/{id}/addItem/{itemId}")
    fun addUserItem(@PathVariable("id") userId: Long, @PathVariable("itemId") itemId: Long): ResponseEntity<Stats> {
        println("POST: Adding item $itemId to user $userId's list of consumed items.")
        try {
            val stats = userService.addUserItem(userId, itemId)
            return ResponseEntity(stats, HttpStatus.OK)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @PutMapping("/{id}")
    fun updateUser(@PathVariable("id") userId: Int, @RequestBody user: User): ResponseEntity<User> {
        val userToUpdate = userRepository.findById(userId.toLong())
        return if (userToUpdate.isPresent) {
            val updatedUser = userRepository.save(user)
            ResponseEntity(updatedUser, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") userId: Int): ResponseEntity<Unit> {
        val user = userRepository.findById(userId.toLong())
        return if (user.isPresent) {
            userRepository.delete(user.get())
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
    @DeleteMapping("clear/key = {key}")
    fun clearUsers(@PathVariable("key") key: String): ResponseEntity<Unit> {
        if(key != "2003") {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }
        userRepository.deleteAll()
        println("DELETE: Deleted all users.")
        return ResponseEntity(HttpStatus.OK)
    }


}