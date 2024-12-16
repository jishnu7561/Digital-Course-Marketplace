package com.example.digital_course_marketplace.repository

import com.example.digital_course_marketplace.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findByTransactionDateBetween(startDate: LocalDate, endDate: LocalDate): List<Transaction>

}