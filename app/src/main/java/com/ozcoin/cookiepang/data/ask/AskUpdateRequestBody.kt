package com.ozcoin.cookiepang.data.ask

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AskUpdateRequestBody(
    val title: String,
    val status: AskEntityStateType,
    val categoryId: Int
)
