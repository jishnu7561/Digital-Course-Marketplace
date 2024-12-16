package com.example.digital_course_marketplace.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

@Entity
@Table(name = "course")
data class Course(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @field:NotBlank(message = "Title cannot be empty.")
        val title: String,

        @field:NotBlank(message = "Description cannot be empty.")
        val description: String,

        @field:Positive(message = "Price must be positive.")
        val price: Double,

        @ManyToOne
        @JoinColumn(name = "creator_id")
        var creator: User?,

        @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
        val purchases: List<Transaction> = mutableListOf()
)
