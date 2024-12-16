package com.example.digital_course_marketplace.repository

import com.example.digital_course_marketplace.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(@Param("email") username: String?): Optional<User>

    @Query("SELECT u FROM User u WHERE u.role IN ('CUSTOMER', 'CREATOR')")
    fun findCustomersAndCreators(): List<User>


}