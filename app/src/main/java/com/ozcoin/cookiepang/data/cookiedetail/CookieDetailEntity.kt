package com.ozcoin.cookiepang.data.cookiedetail

import androidx.annotation.Keep
import com.ozcoin.cookiepang.data.category.CategoryEntity
import com.ozcoin.cookiepang.data.category.toDomain
import com.ozcoin.cookiepang.data.cookie.CookieStatusType
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.usercategory.toCookieCardStyle
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CookieDetailEntity(
    val cookieId: Int,
    val question: String,
    val answer: String?,
    val cookieStatus: CookieStatusType,
    val collectorId: Int,
    val collectorName: String,
    val collectorProfileUrl: String?,
    val creatorId: Int,
    val creatorName: String,
    val creatorProfileUrl: String?,
    val contractAddress: String,
    val histories: List<HistoryEntity>,
    val nftTokenId: Int,
    val viewCount: Int,
    val price: Int,
    val myCookie: Boolean,
    val category: CategoryEntity
)

fun CookieDetailEntity.toDomain(): CookieDetail {
    return CookieDetail(
        isMine = myCookie,
        userCategory = category.toDomain(),
        viewCount = viewCount,
        question = question,
        cookieCardStyle = category.toDomain().categoryColorStyle.toCookieCardStyle(),
        answer = answer,
        hammerPrice = price,
        collectorThumbnailUrl = collectorProfileUrl ?: "",
        collectorName = collectorName,
        creatorName = creatorName,
        creatorThumbnailUrl = creatorProfileUrl ?: "",
        contractAddress = contractAddress,
        tokenAddress = nftTokenId.toString(),
        cookieHistory = histories.map { it.toDomain() },
        isHidden = cookieStatus == CookieStatusType.HIDDEN,
        isOnSale = true,
        collectorUserId = collectorId.toString(),
        creatorUserId = creatorId.toString(),
        nftTokenId = nftTokenId,
        cookieId = cookieId
    )
}
