package com.example.digital_course_marketplace.service

import com.example.digital_course_marketplace.dto.UserDto
import com.example.digital_course_marketplace.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService (val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        return userRepository.findByEmail(username).orElseThrow{UsernameNotFoundException("user not found")}
    }

    fun getAllUsers(): List<UserDto> {

        val userDetails:List<UserDto> = userRepository.findCustomersAndCreators().map {
            user ->  UserDto(user.email,user.id,user.role)
        }
        return userDetails
    }

}