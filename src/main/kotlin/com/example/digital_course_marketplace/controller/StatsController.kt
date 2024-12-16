package com.example.digital_course_marketplace.controller

import com.example.digital_course_marketplace.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/stats")
class StatsController( val transactionService: TransactionService) {

    // Get stats based on date
    @GetMapping
    fun getStatistics(
            @RequestParam startDate: String,
            @RequestParam endDate: String
    ): ResponseEntity<Map<String, Any>> {

        val start = LocalDate.parse(startDate, )
        val end = LocalDate.parse(endDate, )

        val statistics = transactionService.getStatistics(start, end)

        return ResponseEntity(statistics, HttpStatus.OK)
    }
}