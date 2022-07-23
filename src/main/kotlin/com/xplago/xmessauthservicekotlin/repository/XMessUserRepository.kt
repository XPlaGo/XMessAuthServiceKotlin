package com.xplago.xmessauthservicekotlin.repository

import com.xplago.xmessauthservicekotlin.model.XMessUser
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.transaction.annotation.Transactional

interface XMessUserRepository : MongoRepository<XMessUser, Long> {

    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean

    fun findByEmail(email: String): XMessUser?
    fun findByUsername(username: String): XMessUser?

    @Transactional
    fun deleteByEmail(email: String): Void
    @Transactional
    fun deleteByUsername(username: String): Void
}