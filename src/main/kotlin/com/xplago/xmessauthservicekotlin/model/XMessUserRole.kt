package com.xplago.xmessauthservicekotlin.model

import org.springframework.security.core.GrantedAuthority

enum class XMessUserRole : GrantedAuthority {
    ROLE_CLIENT {
        override fun getAuthority(): String = name
                },
    ROLE_ADMIN {
        override fun getAuthority(): String = name
    }
}