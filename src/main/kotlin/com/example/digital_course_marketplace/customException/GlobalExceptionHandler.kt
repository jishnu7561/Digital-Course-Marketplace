package com.example.digital_course_marketplace.customException

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun method(ex:MethodArgumentNotValidException):ResponseEntity<Map<String, String>> {

        val errors = mutableMapOf<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val message = error.defaultMessage ?: "Invalid value"
            errors[fieldName] = message
        }

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUserNotFoundException(ex: UsernameNotFoundException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST
        return ResponseEntity<ErrorResponse> (
                ErrorResponse(
                        status,
                        ex.message
                ),status
        )
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(ex: BadCredentialsException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST
        return ResponseEntity<ErrorResponse> (
                ErrorResponse(
                        status,
                        "Invalid email or password"
                ),status
        )
    }

    @ExceptionHandler(AlreadyExistsException::class)
    fun handleAlreadyExistsException(ex: AlreadyExistsException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.CONFLICT
        return ResponseEntity<ErrorResponse> (
                ErrorResponse(
                        status,
                        ex.message
                ),status
        )
    }
}