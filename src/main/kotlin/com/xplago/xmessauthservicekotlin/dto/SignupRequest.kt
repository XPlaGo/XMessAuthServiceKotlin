package com.xplago.xmessauthservicekotlin.dto

import com.xplago.xmessauthservicekotlin.model.XMessUserRole
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class SignupRequest(
    @field:Pattern(regexp = "^[a-zA-Zа-яА-Я0-9-_ ]+$", message = "Contains invalid characters")
    @field:Size(min=6, max=20, message = "The minimum length of the username is 6 characters")
    @field:NotEmpty(message = "The username is required")
    var username: String,

    @field:Email(message = "Invalid email format")
    @field:NotEmpty(message = "The email is required")
    var email: String,

    @field:Size(min=6, max=255, message = "The minimum length of the password is 8 characters")
    @field:NotEmpty(message = "The password is required")
    var password: String
) {
}