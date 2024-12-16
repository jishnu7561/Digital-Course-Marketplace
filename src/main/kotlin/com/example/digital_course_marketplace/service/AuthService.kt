package com.example.digital_course_marketplace.service

import com.example.digital_course_marketplace.customException.AlreadyExistsException
import com.example.digital_course_marketplace.dto.LoginResponse
import com.example.digital_course_marketplace.dto.RegistrationDto
import com.example.digital_course_marketplace.model.Role
import com.example.digital_course_marketplace.model.User
import com.example.digital_course_marketplace.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val passwordEncoder: PasswordEncoder,
        private val jwtService: JwtService,
        private val authenticationManager: AuthenticationManager,
        private val userRepository: UserRepository
) {

    fun registerUser(regDto: RegistrationDto):String {
        print("User details, email: ${regDto.email} , password: ${passwordEncoder.encode(regDto.password)}  , role: ${regDto.role}")
        val role = when (regDto.role) {
            Role.Admin -> Role.Admin
            Role.Creator -> Role.Creator
            Role.Customer -> Role.Customer
            else -> throw IllegalArgumentException("Invalid role: ${regDto.role}")
        }
        val user = User(
                email= regDto.email,
                encodedPassword = passwordEncoder.encode(regDto.password),
                role = role
        )

        val existingUser = userRepository.findByEmail(regDto.email)
        if (existingUser != null) {

            throw AlreadyExistsException("User with the email '${regDto.email}' already exists.")
        }

        userRepository.save(user)
        return "Saved Successfully"
    }

    fun authenticateUser(regRequest:RegistrationDto): LoginResponse {

            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                            regRequest.email,
                            regRequest.password
                    )
            )

            val user = userRepository.findByEmail(regRequest.email).orElseThrow{UsernameNotFoundException("user not found with email: ${regRequest.email} .")}
            val jwt = jwtService.generateToken(user.email)
           return LoginResponse(
                   user.email,
                   user.id,
                   jwt
           )
    }


}