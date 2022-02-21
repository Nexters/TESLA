package com.ozcoin.cookiepang.data.cookie

import kotlinx.serialization.Serializable

@Serializable
data class CookieListResponse(
    val cookies: List<CookieEntity>,
    val page: Int,
    val size: Int,
    val totalCount: Int
)
