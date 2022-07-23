package com.xplago.xmessauthservicekotlin.security

import com.xplago.xmessauthservicekotlin.model.XMessUserRole
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

import com.xplago.xmessauthservicekotlin.exception.CustomException
import com.xplago.xmessauthservicekotlin.model.XMessUser

@Component
class JwtTokenProvider @Autowired constructor(
    @Value("\${security.jwt.token.secret-key}")
    var secretKey: String,

    @Value("\${security.jwt.token.expire-length:3600000}")
    var validityInMilliseconds: Long = 3600000,

    var XMUserDetailsService: XMessUserDetailsService
) {
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.encodeToByteArray())
    }

    fun createToken(user: XMessUser?): String? {
        if (user == null) return null

        val claims = Jwts.claims().setSubject(user.username)
        claims["userId"] = user.id
        var roles = user.userRoles?.map { SimpleGrantedAuthority(it.authority).toString() }?.joinToString(" ")
        println(roles)
        claims["auth"] = roles
        claims["accountExpired"] = user.accountExpired;
        claims["accountLocked"] = user.accountLocked;
        claims["credentialsExpired"] = user.credentialsExpired;
        claims["disabled"] = user.disabled;

        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = XMUserDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String): String = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7);
        } else {
            null
        }
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            throw CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: IllegalArgumentException) {
            throw CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}