package com.xplago.xmessauthservicekotlin.security

import com.xplago.xmessauthservicekotlin.exception.UserNotFoundException
import com.xplago.xmessauthservicekotlin.model.XMessUser
import com.xplago.xmessauthservicekotlin.repository.XMessUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class XMessUserDetailsService @Autowired constructor(
    val userRepository: XMessUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = username?.let { userRepository.findByUsername(it) }
        try {
            return buildDetails(user)
        } catch (e: UserNotFoundException) {
            throw UsernameNotFoundException("User with username \"$username\" not found")
        }
    }

    fun loadUSerByEmail(email: String?): UserDetails {
        val user = email?.let { userRepository.findByEmail(it) }
        try {
            return buildDetails(user)
        } catch (e: UserNotFoundException) {
            throw UserNotFoundException("User with email \"$email\" not found")
        }
    }

    private fun buildDetails(user: XMessUser?): UserDetails {
        if (user == null) throw UserNotFoundException("User not found")
        else return User
            .withUsername(user.username)
            .password(user.password)
            .authorities(user.userRoles)
            .accountExpired(user.accountExpired)
            .accountLocked(user.accountLocked)
            .credentialsExpired(user.credentialsExpired)
            .disabled(user.disabled)
            .build()
    }

}