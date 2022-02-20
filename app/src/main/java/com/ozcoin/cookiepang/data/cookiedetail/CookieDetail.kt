package com.ozcoin.cookiepang.data.cookiedetail

data class CookieDetail(
    val answer: String,
    val category: Category,
    val collectorName: String,
    val collectorProfileUrl: String,
    val contractAddress: String,
    val creatorName: String,
    val creatorProfileUrl: String,
    val histories: List<History>,
    val myCookie: Boolean,
    val nftTokenId: Int,
    val price: Int,
    val question: String,
    val viewCount: Int
)
