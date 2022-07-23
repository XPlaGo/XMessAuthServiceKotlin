package com.xplago.xmessauthservicekotlin.security

import com.xplago.xmessauthservicekotlin.model.RefreshToken
import com.xplago.xmessauthservicekotlin.model.XMessUser
import com.xplago.xmessauthservicekotlin.repository.RefreshTokenRepository
import com.xplago.xmessauthservicekotlin.repository.XMessUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class RefreshTokenProvider @Autowired constructor(
    @Value("\${security.jwt.refreshExpirationMs:86400000}")
    private val refreshExpirationMs: Long,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: XMessUserRepository
) {
    fun findByToken(token: String) = refreshTokenRepository.findByToken(token)

    fun createRefreshToken(user: XMessUser): RefreshToken {
        val refreshToken = RefreshToken()
        refreshToken.user = user
        refreshToken.expiryDate = Date(Date().time + refreshExpirationMs)
        refreshToken.token = UUID.randomUUID().toString()
        return refreshTokenRepository.save(refreshToken)
    }

    fun verifyExpiration(token: RefreshToken) = token.expiryDate?.compareTo(Date())!! >= 0

    @Transactional
    fun deleteByUserId(userId: String) = refreshTokenRepository.deleteByUserId(userId)
}