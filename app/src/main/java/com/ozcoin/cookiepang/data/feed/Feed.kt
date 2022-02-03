package com.ozcoin.cookiepang.data.feed

data class Feed(
    val question: String,
    val contents: String,
    val thumnail: ByteArray ? = null,
    val viewCount: Int,
    val price: Int
)
