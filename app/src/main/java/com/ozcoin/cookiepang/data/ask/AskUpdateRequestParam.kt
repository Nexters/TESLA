package com.ozcoin.cookiepang.data.ask

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class AskUpdateRequestParam(
    val status: AskEntityStateType,
    val title: String
)
