package com.xplago.xmessauthservicekotlin.dto

import javax.validation.constraints.NotBlank

class RefreshResponse(
    @NotBlank
    var token: String,
    @NotBlank
    var username: String
) {
}