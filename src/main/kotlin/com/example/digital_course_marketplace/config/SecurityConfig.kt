package com.example.digital_course_marketplace.config

import com.example.digital_course_marketplace.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig (
        private val userService: UserService,
        private val jwtFilter: JwtFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
                .csrf { it.disable() }
                .authorizeHttpRequests { auth ->
                    auth
                            .requestMatchers("/api/auth/**")
                            .permitAll()
                            .requestMatchers("/api/admin/**")
                            .hasAuthority("Admin")
                            .requestMatchers("/api/user/course/**")
                            .hasAnyAuthority("Creator")
                            .requestMatchers("/api/user/courses","/api/user/buy/**")
                            .hasAnyAuthority("Customer")
                            .requestMatchers("/api/user/stats")
                            .hasAnyAuthority("Customer","Admin","Creator")
                            .anyRequest()
                            .authenticated()
                }
                .sessionManagement { session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
                .build()
    }

    @Bean
    fun passwordEncoder():PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider():AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(userService)
        return provider
    }

    @Bean
    fun authenticationManager(config:AuthenticationConfiguration):AuthenticationManager = config.authenticationManager





}