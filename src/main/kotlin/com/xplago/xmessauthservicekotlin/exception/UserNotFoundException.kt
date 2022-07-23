package com.xplago.xmessauthservicekotlin.exception

import org.springframework.security.core.AuthenticationException

open class UserNotFoundException : AuthenticationException {
    constructor(msg: String?) : super(msg) {}
    constructor(msg: String?, cause: Throwable?) : super(msg, cause) {}
}