package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import com.ozcoin.cookiepang.data.category.CategoryEntity
import com.ozcoin.cookiepang.data.category.toDomain
import com.ozcoin.cookiepang.domain.cookie.Cookie
import com.ozcoin.cookiepang.domain.usercategory.toCookieCardStyle
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CookieEntity(
    val cookieId: Int,
    val nftTokenId: Int,
    val cookieImageUrl: String?,
    val ownedUserId: Int,
    val cookieStatus: CookieStatusType,
    val category: CategoryEntity
)

fun CookieEntity.toDomain(): Cookie {
    return Cookie(
        cookieId = cookieId.toString(),
        isHidden = cookieStatus == CookieStatusType.HIDDEN,
        cookieBoxImgUrl = null,
        cookieBoxCoverImgUrl = null,
        cookieImgUrl = null,
        cookieCardStyle = category.toDomain().categoryColorStyle.toCookieCardStyle(),
        ownedUserId = ownedUserId.toString()
    )
}
