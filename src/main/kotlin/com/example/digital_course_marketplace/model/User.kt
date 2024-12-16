package com.example.digital_course_marketplace.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "USERS")
data class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0 ,

        @field:NotBlank(message = "Email cannot be empty.")
        @field:Email(message = "Email should be valid.")
        @Column(unique = true)
        var email:String,

        @field:NotBlank(message = "Password cannot be empty.")
        @field:Size(min = 6, message = "Password should be at least 6 characters.")
        var encodedPassword: String,

        @Enumerated(EnumType.STRING)
        var role:Role,

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        val purchasedCourses: List<Transaction> = mutableListOf()

) : UserDetails {
    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return encodedPassword
    }

    override fun getUsername(): String {
        return email
    }
}

enum class Role {

    Creator,
    Admin,
    Customer
}
