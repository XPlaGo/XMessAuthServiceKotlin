package com.xplago.xmessauthservicekotlin.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Document
open class XMessUser(

    @Id
    var id: String? = null,

    @Size(min = 6, max = 255, message = "Minimum username length: 4 characters")
    @Indexed(unique = true)
    var username: String,

    @Email
    @Indexed(unique = true)
    var email: String,

    @Size(min = 8, message = "Minimum password length: 8 characters")
    @Indexed(unique = true)
    var password: String,

    var userRoles: MutableList<XMessUserRole>? = mutableListOf(),

    var accountExpired: Boolean,

    var accountLocked: Boolean,

    var credentialsExpired: Boolean,

    var disabled: Boolean
) {
    constructor(
        username: String,
        email: String,
        password: String
    ) : this(null, username, email, password, null, false, false, false, false)
}