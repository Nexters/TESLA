package com.ozcoin.cookiepang.domain.feed

data class Feed(
    val isHidden: Boolean,
    val userThumbnailUrl: String,
    val createdTimeStamp: String,
    val question: String,
    val answer: String?,
    val viewCount: Int,
    val hammerPrice: Int
)
