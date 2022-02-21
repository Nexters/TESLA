package com.ozcoin.cookiepang.data.ask

import kotlinx.serialization.Serializable

@Serializable
data class AskUpdateRequestParam(
    val status: AskEntityStateType,
    val title: String
)
