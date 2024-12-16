package com.example.digital_course_marketplace.controller

import com.example.digital_course_marketplace.dto.UserDto
import com.example.digital_course_marketplace.model.User
import com.example.digital_course_marketplace.service.TransactionService
import com.example.digital_course_marketplace.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController( val userService: UserService,
        val transactionService: TransactionService) {

    // Get all user details
    @GetMapping("/user")
    fun getUsers():ResponseEntity<List<UserDto>> {
        val allUsers = userService.getAllUsers()
        return ResponseEntity(allUsers, HttpStatus.OK)
    }

}