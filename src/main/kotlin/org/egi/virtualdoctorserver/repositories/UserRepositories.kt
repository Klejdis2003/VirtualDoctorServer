package org.egi.virtualdoctorserver.repositories

import org.egi.virtualdoctorserver.model.*
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, Long> {

    /**
     * @param email the email of the user
     * @return the user with the given email if it exists or an empty optional if it does not
     */
    fun findByEmail(email: String): Optional<User>

    /**
     * @param username the username of the user
     * @return the user with the given username if it exists or an empty optional if it does not
     */
    fun findByUsername(username: String): Optional<User>

    fun existsByEmail(email: String): Boolean


}



