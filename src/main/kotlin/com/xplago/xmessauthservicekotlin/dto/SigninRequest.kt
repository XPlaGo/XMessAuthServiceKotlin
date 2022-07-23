package com.xplago.xmessauthservicekotlin.dto

import javax.validation.constraints.NotEmpty

class SigninRequest(
    @field:NotEmpty(message = "The username/email is required")
    var username: String,

    @field:NotEmpty(message = "The password is required")
    var password: String
) {
}