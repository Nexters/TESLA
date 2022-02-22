package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CookieListResponse(
    val cookies: List<CookieEntity>,
    val page: Int,
    val size: Int,
    val totalCount: Int
)
