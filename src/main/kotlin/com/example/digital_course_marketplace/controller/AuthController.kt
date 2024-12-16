package com.example.digital_course_marketplace.controller

import com.example.digital_course_marketplace.dto.LoginResponse
import com.example.digital_course_marketplace.dto.RegistrationDto
import com.example.digital_course_marketplace.model.User
import com.example.digital_course_marketplace.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/signup")
    fun register(@Valid @RequestBody regRequest: RegistrationDto): ResponseEntity<Any> {
        val savedUser = authService.registerUser(regRequest);
        return ResponseEntity(savedUser, HttpStatus.ACCEPTED)
    }

    @PostMapping("/login")
    fun authenticate(@Valid @RequestBody regRequest: RegistrationDto):ResponseEntity<LoginResponse> {
        val authenticated = authService.authenticateUser(regRequest)
        return ResponseEntity(authenticated,HttpStatus.ACCEPTED)
    }

}