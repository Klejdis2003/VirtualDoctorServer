package org.egi.virtualdoctorserver.controller

import org.egi.virtualdoctorserver.dto.UserDietaryRequirementsDTO
import org.egi.virtualdoctorserver.model.User
import org.egi.virtualdoctorserver.persistence.UserRepository
import org.egi.virtualdoctorserver.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userRepository: UserRepository) {
    @GetMapping
    fun getAllUsers() = userRepository.findAll().toList()

    @PostMapping("")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        if(userRepository.findByUsername(user.username).isPresent){
            return ResponseEntity(HttpStatus.CONFLICT)
        }
        val createdUser = userRepository.save(user)
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

    @GetMapping("/username/{username}/dietary_requirements")
    fun getUserDietaryRequirements(@PathVariable("username") username: String): ResponseEntity<UserDietaryRequirementsDTO> {

        val user  = try{
            userRepository.findDietaryRequirements(username)[0]
        }
        catch (e: Exception){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        val userDietaryRequirements =
            UserDietaryRequirementsDTO(
                user[0] as Int,
                user[1] as Int,
                user[2] as Int,
                user[3] as Int,
                user[4] as Boolean,
                user[5] as Boolean)
        return ResponseEntity(userDietaryRequirements, HttpStatus.OK)
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