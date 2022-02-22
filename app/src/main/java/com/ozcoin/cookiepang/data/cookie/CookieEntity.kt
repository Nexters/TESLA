package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CookieEntity(
    val id: Int,
    val title: String,
    val price: Int,
    val imageUrl: String?,
    val authorUserId: Int,
    val categoryId: Int,
    val createdAt: String,
    val fromBlockAddress: Int,
    val nftTokenId: Int,
    val ownedUserId: Int,
    val status: CookieStatusType,
    val txHash: String
)
