package com.xplago.xmessauthservicekotlin.exception

import org.springframework.http.HttpStatus

class CustomException(
    override val message: String,
    val httpStatus: HttpStatus
) : RuntimeException() {

}