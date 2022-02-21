package com.ozcoin.cookiepang.data.timeline

import com.ozcoin.cookiepang.data.category.CategoryEntity
import com.ozcoin.cookiepang.domain.feed.CookieCardStyle
import com.ozcoin.cookiepang.domain.feed.Feed
import com.ozcoin.cookiepang.utils.DateSerializer
import com.ozcoin.cookiepang.utils.DateUtil
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class TimeLineEntity(
    val cookieId: Int,
    val creatorId: Int,
    val creatorName: String,
    val creatorProfileUrl: String?,
    val question: String,
    val answer: String?,
    val contractAddress: String,
    val nftTokenId: Int,
    val viewCount: Int,
    val cookieImageUrl: String?,
    val price: Int,
    val myCookie: Boolean,
    val category: CategoryEntity,
    @Serializable(DateSerializer::class)
    val createdAt: Date
)

fun TimeLineEntity.toDomain(): Feed {
    return Feed(
        isHidden = answer == null,
        question = question,
        cookieId = cookieId,
        createdTimeStamp = DateUtil.convertToFeedTimeStamp(createdAt),
        viewCount = viewCount,
        hammerPrice = price,
        userProfileId = creatorName,
        userThumbnailUrl = creatorProfileUrl ?: "",
        answer = answer,
        cookieCardStyle = CookieCardStyle.BLUE
    )
}
