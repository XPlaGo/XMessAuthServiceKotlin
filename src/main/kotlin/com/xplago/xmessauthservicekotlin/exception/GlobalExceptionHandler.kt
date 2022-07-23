package com.xplago.xmessauthservicekotlin.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.IOException
import java.nio.file.AccessDeniedException
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class GlobalExceptionHandler {
    /*@Bean
    fun errorAttributes(): ErrorAttributes = {
        super.
    }*/

    @ExceptionHandler(CustomException::class)
    @Throws(IOException::class)
    fun handleCustomException(res: HttpServletResponse, ex: CustomException): ResponseEntity<String> =
        ResponseEntity.status(ex.httpStatus).body(ex.message)

    @ExceptionHandler(AccessDeniedException::class)
    @Throws(IOException::class)
    fun handleAccessDeniedException(res: HttpServletResponse): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied")

    @ExceptionHandler(RefreshTokenException::class)
    @Throws(IOException::class)
    fun handleRefreshTokenException(res: HttpServletResponse): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expected refresh token")

    @ExceptionHandler(UserNotFoundException::class)
    @Throws(IOException::class)
    fun handleUserNotFoundException(res: HttpServletResponse): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found")

    @ExceptionHandler(AuthenticationException::class)
    @Throws(IOException::class)
    fun handleAuthenticationException(res: HttpServletResponse): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication exception")

//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    @Throws(IOException::class)
//    fun handleValidationException(res: HttpServletResponse, e: MethodArgumentNotValidException): ResponseEntity<String> =
//        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
}