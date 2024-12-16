package com.example.digital_course_marketplace.dto

import com.example.digital_course_marketplace.model.Role

data class UserDto(val email: String,
                   val id: Long,
                   val role: Role)
