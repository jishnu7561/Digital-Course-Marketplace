package com.example.digital_course_marketplace.service

import com.example.digital_course_marketplace.customException.AlreadyExistsException
import com.example.digital_course_marketplace.dto.CourseDto
import com.example.digital_course_marketplace.model.Course
import com.example.digital_course_marketplace.model.User
import com.example.digital_course_marketplace.repository.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {

    fun createCourse(course: Course, loggedInUser: User): String{
        val existingCourse = courseRepository.findByTitleIgnoreCase(course.title)
        if (existingCourse != null) {

            throw AlreadyExistsException("A course with the title '${course.title}' already exists.")
        }
        course.creator =loggedInUser
        courseRepository.save(course);
        return "Course created successfully."
    }

    fun getCoursesByCreator(creator: User): List<CourseDto> {
        val courseDetails:List<CourseDto> = courseRepository.findByCreator(creator)
                .map {
                    course ->  CourseDto(course.id, course.title, course.description, course.price)
                }
        return courseDetails;
    }

    fun searchCourses(search: String): List<CourseDto> {
        val courseDetails:List<CourseDto> = courseRepository.findByTitleContainingOrDescriptionContaining(search,search)
                .map {
                    course ->  CourseDto(course.id, course.title, course.description, course.price)
                }
        return courseDetails;
    }

    fun getAllCourses(): List<CourseDto> {
        val courseDetails:List<CourseDto> = courseRepository.findAll()
                .map {
                    course ->  CourseDto(course.id, course.title, course.description, course.price)
                }
        return courseDetails;
    }

}