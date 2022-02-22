package com.ozcoin.cookiepang.data.timeline

import androidx.annotation.Keep
import com.ozcoin.cookiepang.data.category.CategoryEntity
import com.ozcoin.cookiepang.domain.feed.CookieCardStyle
import com.ozcoin.cookiepang.domain.feed.Feed
import com.ozcoin.cookiepang.utils.DateUtil
import kotlinx.serialization.Serializable

@Keep
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
    val createdAt: String
)

fun TimeLineEntity.toDomain(): Feed {
    return Feed(
        isHidden = !myCookie,
        question = question,
        cookieId = cookieId,
        createdTimeStamp = DateUtil.convertToFeedTimeStamp(createdAt.split(".")[0]),
        viewCount = viewCount,
        hammerPrice = price,
        userProfileId = creatorName,
        userThumbnailUrl = creatorProfileUrl ?: "",
        answer = answer,
        cookieCardStyle = CookieCardStyle.BLUE
    )
}
