package com.ozcoin.cookiepang.domain.feed

data class Feed(
    val isHidden: Boolean,
    val userThumbnailUrl: String,
    val userProfileId: String,
    val createdTimeStamp: String,
    val question: String,
    val answer: String?,
    val cookieCardStyle: CookieCardStyle,
    val viewCount: Int,
    val hammerPrice: Int,
    val id: String = "($isHidden)$userProfileId/$question/$createdTimeStamp",
)
