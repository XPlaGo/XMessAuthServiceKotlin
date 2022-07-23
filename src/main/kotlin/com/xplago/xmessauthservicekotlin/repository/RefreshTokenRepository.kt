package com.xplago.xmessauthservicekotlin.repository

import com.xplago.xmessauthservicekotlin.model.RefreshToken
import org.springframework.data.mongodb.repository.MongoRepository

interface RefreshTokenRepository : MongoRepository<RefreshToken, Long> {
    fun findByToken(token: String): RefreshToken?
    fun deleteByUserId(userId: String): Void
}