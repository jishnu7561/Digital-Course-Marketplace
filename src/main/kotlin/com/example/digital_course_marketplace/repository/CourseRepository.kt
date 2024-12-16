package com.example.digital_course_marketplace.repository

import com.example.digital_course_marketplace.model.Course
import com.example.digital_course_marketplace.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.util.MultiValueMap
import java.util.Optional

@Repository
interface CourseRepository : JpaRepository<Course,Long> {

    fun findByCreator(creator: User): List<Course>

    fun findByTitleContainingOrDescriptionContaining(title: String, description: String): List<Course>

    fun findByTitleIgnoreCase(title: String): Optional<Course>
}