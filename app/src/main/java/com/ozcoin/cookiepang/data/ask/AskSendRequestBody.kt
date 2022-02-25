package com.ozcoin.cookiepang.data.ask

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AskSendRequestBody(
    val categoryId: Int,
    val receiverUserId: Int,
    val senderUserId: Int,
    val title: String
)
