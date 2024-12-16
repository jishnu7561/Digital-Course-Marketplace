package com.example.digital_course_marketplace.service

import com.example.digital_course_marketplace.model.Transaction
import com.example.digital_course_marketplace.model.User
import com.example.digital_course_marketplace.repository.CourseRepository
import com.example.digital_course_marketplace.repository.TransactionRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TransactionService(
        val courseRepository: CourseRepository,
        val transactionRepository: TransactionRepository
) {

    fun buyCourse(user: User, courseId: Long) {

        val course = courseRepository.findById(courseId).orElseThrow{UsernameNotFoundException("Course not found with the id: $courseId")}
        val transaction = Transaction(
                user = user,
                course = course,
                )
        transactionRepository.save(transaction);

    }

    fun getStatistics(startDate: LocalDate, endDate: LocalDate): Map<String, Any> {
        val transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate)

        val totalAmountPaid = transactions.sumOf { it.course.price }

        val result = mutableMapOf<String, Any>()
        result["totalAmountPaid"] = totalAmountPaid
        result["purchasedCourses"] = transactions.map {
            mapOf(
                    "courseId" to it.course.id,
                    "courseTitle" to it.course.title,
                    "amountPaid" to it.course.price,
                    "transactionDate" to it.transactionDate
            )
        }

        return result
    }

}