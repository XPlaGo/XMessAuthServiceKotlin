package com.xplago.xmessauthservicekotlin.dto

import javax.validation.constraints.NotBlank

class RefreshRequest(
    @NotBlank
    var refreshToken: String
) {
}