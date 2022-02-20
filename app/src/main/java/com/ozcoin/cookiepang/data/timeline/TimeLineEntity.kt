package com.ozcoin.cookiepang.data.timeline

import com.ozcoin.cookiepang.domain.feed.CookieCardStyle
import com.ozcoin.cookiepang.domain.feed.Feed
import kotlinx.serialization.Serializable

@Serializable
data class TimeLineEntity(
    val answer: String?,
    val collectorName: String,
    val collectorProfileUrl: String?,
    val contractAddress: String,
    val cookieId: Int,
    val cookieImageUrl: String?,
    val createdAt: String,
    val myCookie: Boolean,
    val nftTokenId: Int,
    val price: Int,
    val question: String,
    val viewCount: Int
)

fun TimeLineEntity.toDomain(): Feed {
    return Feed(
        isHidden = answer == null,
        question = question,
        cookieId = cookieId,
        createdTimeStamp = createdAt,
        viewCount = viewCount,
        hammerPrice = price,
        userProfileId = collectorName,
        userThumbnailUrl = collectorProfileUrl ?: "",
        answer = answer,
        cookieCardStyle = CookieCardStyle.BLUE
    )
}
