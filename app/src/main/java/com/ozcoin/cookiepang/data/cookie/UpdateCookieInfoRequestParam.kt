package com.ozcoin.cookiepang.data.cookie

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCookieInfoRequestParam(
    val price: Int,
    val status: String,
    val purchaserUserId: Int
)
