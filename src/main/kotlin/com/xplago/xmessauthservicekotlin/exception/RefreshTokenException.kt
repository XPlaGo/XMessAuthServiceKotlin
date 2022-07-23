package com.xplago.xmessauthservicekotlin.exception

import org.springframework.http.HttpStatus

class RefreshTokenException(
    override val message: String
) : RuntimeException() {

}