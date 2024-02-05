package org.egi.virtualdoctorserver.services

import org.egi.virtualdoctorserver.persistence.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService{

    lateinit var userRepository: UserRepository
    fun getUserDietaryRequirements(username : String) = userRepository.findDietaryRequirements(username)
}