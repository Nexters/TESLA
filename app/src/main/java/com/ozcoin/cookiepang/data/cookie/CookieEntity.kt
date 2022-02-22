package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import com.ozcoin.cookiepang.domain.cookie.Cookie
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CookieEntity(
    val id: Int,
    val title: String,
    val price: Int,
    val imageUrl: String?,
    val authorUserId: Int,
    val ownedUserId: Int,
    val createdAt: String,
    val status: CookieStatusType,
    val nftTokenId: Int,
    val fromBlockAddress: Int,
    val categoryId: Int
)

fun CookieEntity.toDomain(): Cookie {
    return Cookie(
        cookieId = id.toString(),
        isHidden = false,
        cookieBoxImgUrl = "",
        cookieBoxCoverImgUrl = "",
        cookieImgUrl = ""
    )
}
