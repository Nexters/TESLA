package com.ozcoin.cookiepang.data.cookiedetail

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val color: String,
    val id: Int,
    val name: String
)
