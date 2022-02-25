package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CookieResponse(
    val authorUserId: Int,
    val categoryId: Int,
    val createdAt: String,
    val fromBlockAddress: Int,
    val id: Int,
    val imageUrl: String?,
    val nftTokenId: Int,
    val ownedUserId: Int,
    val price: Int,
    val status: String,
    val title: String
)
