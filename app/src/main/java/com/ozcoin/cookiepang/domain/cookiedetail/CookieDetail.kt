package com.ozcoin.cookiepang.domain.cookiedetail

import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistory
import com.ozcoin.cookiepang.domain.editcookie.EditCookie
import com.ozcoin.cookiepang.domain.feed.CookieCardStyle
import com.ozcoin.cookiepang.domain.usercategory.UserCategory

data class CookieDetail(
    val isMine: Boolean,
    val userCategory: UserCategory,
    val viewCount: Int,
    val question: String,
    val cookieCardStyle: CookieCardStyle,
    val answer: String?,
    val hammerPrice: Int,
    val collectorThumbnailUrl: String,
    val collectorName: String,
    val creatorName: String,
    val creatorThumbnailUrl: String,
    val contractAddress: String,
    val tokenAddress: String,
    val cookieHistory: List<CookieHistory>,
    val isHidden: Boolean,
    val cookieId: Int
)

fun CookieDetail.toEditCookie(): EditCookie {
    return EditCookie().apply {
        isEditPricingInfo = true
        question = question
        answer = answer
        hammerCost = hammerPrice.toString()
        selectedCategory = userCategory
    }
}
