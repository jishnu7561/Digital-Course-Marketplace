package com.example.digital_course_marketplace.dto

import com.example.digital_course_marketplace.model.Role
import jakarta.persistence.Column
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegistrationDto(
        @field:NotBlank(message = "Email cannot be empty.")
        @field:Email(message = "Email should be valid.")
        @Column(unique = true)
        val email:String,

        @field:NotBlank(message = "Password cannot be empty.")
        @field:Size(min = 6, message = "Password should be at least 6 characters.")
        val password:String,

        var role:Role?
)
