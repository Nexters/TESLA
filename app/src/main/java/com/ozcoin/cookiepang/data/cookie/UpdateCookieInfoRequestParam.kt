package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UpdateCookieInfoRequestParam(
    val price: Int,
    val status: String,
    val purchaserUserId: Int
)
