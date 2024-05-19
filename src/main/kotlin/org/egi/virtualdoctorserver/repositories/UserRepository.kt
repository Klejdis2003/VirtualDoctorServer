package org.egi.virtualdoctorserver.repositories

import org.egi.virtualdoctorserver.model.NutritionValues
import org.egi.virtualdoctorserver.model.User
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
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

    /**
     * @param username the username of the user
     * @return true if a user with the given username exists, false otherwise
     */


    @Modifying
    @Query("Alter Sequence users RESTART WITH 1 ", nativeQuery = true)
    fun truncateTable()
}

interface NutritionGoalRepository : CrudRepository<NutritionValues, Long> {
    fun save(dietaryRestrictions: NutritionValues): NutritionValues
}

interface NutritionValuesRepository : CrudRepository<NutritionValues, Long> {
    fun save(nutritionValues: NutritionValues): NutritionValues
}

