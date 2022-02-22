package com.ozcoin.cookiepang.data.cookiedetail

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Category(
    val color: String,
    val id: Int,
    val name: String
)
