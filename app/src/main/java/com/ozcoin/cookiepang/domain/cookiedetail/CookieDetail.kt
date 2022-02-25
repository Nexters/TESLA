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
    val collectorUserId: String,
    val collectorThumbnailUrl: String,
    val collectorName: String,
    val creatorUserId: String,
    val creatorName: String,
    val creatorThumbnailUrl: String,
    val contractAddress: String,
    val cookieHistory: List<CookieHistory>,
    var isHidden: Boolean,
    var isOnSale: Boolean,
    val nftTokenId: Int,
    var cookieId: Int
)

fun CookieDetail.toEditCookie(): EditCookie {
    val editCookie = EditCookie()
    editCookie.let {
        it.isEditPricingInfo = true
        it.question = question
        it.answer = answer ?: ""
        it.hammerCost = hammerPrice.toString()
        it.selectedCategory = userCategory
    }
    return editCookie
}
