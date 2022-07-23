package com.xplago.xmessauthservicekotlin.dto

class TokenResponse(
    var refreshToken: String,
    var jwtToken: String,
    var username: String
) {
}