package com.ozcoin.cookiepang.data.cookiedetail

import androidx.annotation.Keep
import com.ozcoin.cookiepang.data.category.CategoryEntity
import com.ozcoin.cookiepang.data.category.toDomain
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.feed.CookieCardStyle
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CookieDetailEntity(
    val answer: String?,
    val category: CategoryEntity,
    val collectorId: Int,
    val collectorName: String,
    val collectorProfileUrl: String?,
    val contractAddress: String,
    val creatorId: Int,
    val creatorName: String,
    val creatorProfileUrl: String?,
    val histories: List<HistoryEntity>,
    val myCookie: Boolean,
    val nftTokenId: Int,
    val price: Int,
    val question: String,
    val viewCount: Int
)

fun CookieDetailEntity.toDomain(): CookieDetail {
    return CookieDetail(
        isMine = myCookie,
        userCategory = category.toDomain(),
        viewCount = viewCount,
        question = question,
        cookieCardStyle = CookieCardStyle.YELLOW,
        answer = answer,
        hammerPrice = price,
        collectorThumbnailUrl = collectorProfileUrl ?: "",
        collectorName = collectorName,
        creatorName = creatorName,
        creatorThumbnailUrl = creatorProfileUrl ?: "",
        contractAddress = contractAddress,
        tokenAddress = nftTokenId.toString(),
        cookieHistory = histories.map { it.toDomain() },
        isHidden = false,
        isOnSale = true,
        cookieId = nftTokenId
    )
}
