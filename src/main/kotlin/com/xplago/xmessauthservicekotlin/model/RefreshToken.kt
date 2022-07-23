package com.xplago.xmessauthservicekotlin.model

import lombok.Builder
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document
@NoArgsConstructor
@Builder
class RefreshToken(
    @Id
    var id: String? = null,

    @DBRef
    var user: XMessUser? = null,

    var token: String? = null,

    var expiryDate: Date? = null
) {

}