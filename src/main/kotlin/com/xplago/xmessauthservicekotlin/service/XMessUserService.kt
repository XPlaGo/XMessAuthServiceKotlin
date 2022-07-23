package com.xplago.xmessauthservicekotlin.service

import com.xplago.xmessauthservicekotlin.dto.RefreshRequest
import com.xplago.xmessauthservicekotlin.dto.RefreshResponse
import com.xplago.xmessauthservicekotlin.dto.TokenResponse
import com.xplago.xmessauthservicekotlin.exception.CustomException
import com.xplago.xmessauthservicekotlin.exception.RefreshTokenException
import com.xplago.xmessauthservicekotlin.exception.UserNotFoundException
import com.xplago.xmessauthservicekotlin.model.XMessUser
import com.xplago.xmessauthservicekotlin.model.XMessUserRole
import com.xplago.xmessauthservicekotlin.repository.RefreshTokenRepository
import com.xplago.xmessauthservicekotlin.repository.XMessUserRepository
import com.xplago.xmessauthservicekotlin.security.JwtTokenProvider
import com.xplago.xmessauthservicekotlin.security.RefreshTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class XMessUserService @Autowired constructor(
    private val userRepository: XMessUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager,
    private val refreshTokenProvider: RefreshTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun signin(username: String, password: String): TokenResponse {
       try {
           val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
           val user = userRepository.findByUsername(username) ?: throw UserNotFoundException("Invalid username/password supplied")
           val refreshToken = refreshTokenProvider.createRefreshToken(user)
           val jwtToken = jwtTokenProvider.createToken(user)
           if (refreshToken.token == null || jwtToken == null) {
               throw throw UserNotFoundException("Invalid username/password supplied")
           }
           return TokenResponse(refreshToken.token!!, jwtToken, username)
       } catch (e: AuthenticationException) {
           throw CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY)
       }
    }

    fun signup(user: XMessUser): TokenResponse {
        if (userRepository.existsByUsername(user.username)) {
            throw CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY)
        } else if (userRepository.existsByEmail(user.email)) {
            throw CustomException("Email is already in use", HttpStatus.UNPROCESSABLE_ENTITY)
        } else {
            user.password = passwordEncoder.encode(user.password)
            user.userRoles = mutableListOf(XMessUserRole.ROLE_CLIENT)
            val savedUser = userRepository.save(user)
            val refreshToken = refreshTokenProvider.createRefreshToken(savedUser)
            val jwtToken = jwtTokenProvider.createToken(user)
            if (refreshToken.token == null || jwtToken == null) {
                throw throw UserNotFoundException("Invalid username/password supplied")
            }
            return TokenResponse(refreshToken.token!!, jwtToken, user.username)
        }
    }

    fun refreshToken(request: RefreshRequest): RefreshResponse {
        val requestRefreshToken = request.refreshToken
        val refreshToken = refreshTokenProvider.findByToken(requestRefreshToken)
        if (refreshToken == null) {
            throw RefreshTokenException("Refresh token doesn't exist")
        } else if (refreshTokenProvider.verifyExpiration(refreshToken)) {
            println("first: ${refreshToken.user?.disabled}")
            if (refreshToken.user == null) {
                throw UserNotFoundException("The update token is not linked to the user")
            } else {
                val jwtToken =
                    jwtTokenProvider.createToken(refreshToken.user)
                        ?: throw UserNotFoundException("The update token is not linked to the user")
                println("second: ${refreshToken.user}")
                return RefreshResponse(jwtToken, refreshToken.user!!.username)
            }
        } else {
            refreshToken.user?.id?.let { refreshTokenRepository.deleteByUserId(it) }
            throw RefreshTokenException("The update token is no longer valid")
        }
    }
}