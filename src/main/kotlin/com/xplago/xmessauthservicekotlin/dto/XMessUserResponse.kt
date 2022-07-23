package com.xplago.xmessauthservicekotlin.dto

import com.xplago.xmessauthservicekotlin.model.XMessUserRole

class XMessUserResponse(
    var id: String,
    var username: String,
    var email: String,
    var userRoles: MutableList<XMessUserRole>,
    var accountExpired: Boolean,
    var accountLocked: Boolean,
    var credentialsExpired: Boolean,
    var disabled: Boolean
) {
}