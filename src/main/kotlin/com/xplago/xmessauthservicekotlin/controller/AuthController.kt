package com.xplago.xmessauthservicekotlin.controller

import com.xplago.xmessauthservicekotlin.dto.*
import com.xplago.xmessauthservicekotlin.model.XMessUser
import com.xplago.xmessauthservicekotlin.service.XMessUserService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
class AuthController @Autowired constructor(
    private val userService: XMessUserService
) {
    @PostMapping("/signin")
    fun signin(@Validated @RequestBody request: SigninRequest): TokenResponse {
        return userService.signin(request.username, request.password)
    }

    @PostMapping("/signup")
    fun signup(@Validated @RequestBody request: SignupRequest): TokenResponse {
        val user = XMessUser(request.username, request.email, request.password)
        return userService.signup(user)
    }

    @PostMapping("/refresh")
    fun refreshToken(@Validated @RequestBody request: RefreshRequest): RefreshResponse {
        return userService.refreshToken(request)
    }
}