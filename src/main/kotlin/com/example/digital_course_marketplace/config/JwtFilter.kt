package com.example.digital_course_marketplace.config

import com.example.digital_course_marketplace.service.JwtService
import com.example.digital_course_marketplace.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter

@Service
class JwtFilter (private val jwtService: JwtService, private val userService: UserService) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain) {

        val authHeader = request.getHeader("Authorization")
        val jwt:String?
        val userEmail:String?

        // Checks for authorization header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response)
            return
        }

        // Extract JWT token from Authorization header
        jwt = authHeader.substring(7)

        // Extract Email from JWT token
        userEmail = jwtService.extractUsername(jwt)

        // Check if userEmail is valid and user is not already authenticated
        if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userService.loadUserByUsername(userEmail);

            // Validate JWT and set authentication if valid
            if (jwtService.isTokenValid(jwt, userDetails)) {
                val authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                )

                authToken.details = (WebAuthenticationDetailsSource().buildDetails(request))

                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request,response)

    }

}