package com.ozcoin.cookiepang.data.timeline

import kotlinx.serialization.Serializable

@Serializable
data class TimeLine(
    val answer: String,
    val collectorName: String,
    val collectorProfileUrl: String,
    val contractAddress: String,
    val cookieId: Int,
    val cookieImageUrl: String,
    val createdAt: String,
    val myCookie: Boolean,
    val nftTokenId: Int,
    val price: Int,
    val question: String,
    val viewCount: Int
)
