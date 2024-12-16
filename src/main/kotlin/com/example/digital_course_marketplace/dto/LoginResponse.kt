package com.example.digital_course_marketplace.dto


data class LoginResponse(
        val email: String,
        val id: Long,
        val jwtToken: String
)