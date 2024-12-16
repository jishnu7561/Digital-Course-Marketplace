package com.example.digital_course_marketplace.controller

import com.example.digital_course_marketplace.dto.CourseDto
import com.example.digital_course_marketplace.model.Course
import com.example.digital_course_marketplace.model.User
import com.example.digital_course_marketplace.repository.UserRepository
import com.example.digital_course_marketplace.service.CourseService
import com.example.digital_course_marketplace.service.TransactionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
        val courseService: CourseService,
        val transactionService: TransactionService
) {


    // Create course
    @PostMapping("/course")
    fun createCourse(@Valid @RequestBody course: Course, @AuthenticationPrincipal loggedInUser: User): ResponseEntity<Any> {
        val createdCourse = courseService.createCourse(course, loggedInUser)
        print("logged is user is : ${loggedInUser.email}")
        return ResponseEntity(createdCourse,HttpStatus.CREATED)
    }

    // Get course details based on creator
    @GetMapping("/course")
    fun getCourses(@AuthenticationPrincipal loggedInUser: User): ResponseEntity<List<CourseDto>> {
        val courses = courseService.getCoursesByCreator(loggedInUser)
        return ResponseEntity(courses,HttpStatus.OK)
    }

    // Get course details based on search
    @GetMapping("/courses")
    fun getCourses(@RequestParam(required = false) search: String?): ResponseEntity<List<CourseDto>> {
        val courses = if (search != null) {
            courseService.searchCourses(search)
        } else {
            courseService.getAllCourses()
        }
        return ResponseEntity(courses, HttpStatus.OK)
    }

    // Buy course
    @GetMapping("/buy/course/{id}")
    fun buyCourse(@PathVariable id: Long, @AuthenticationPrincipal user: User): ResponseEntity<String> {
        return try {
            transactionService.buyCourse(user, id)
            ResponseEntity.ok("Course purchased successfully and added to your library.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Failed to purchase the course.")
        }
    }

}