package com.ozcoin.cookiepang.data.ask

import kotlinx.serialization.Serializable

@Serializable
data class AskUpdateRequestBody(
    val title: String,
    val status: AskEntityStateType,
    val categoryId: Int
)
