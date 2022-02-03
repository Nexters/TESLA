package com.ozcoin.cookiepang.data.feed

import com.ozcoin.cookiepang.domain.feed.Feed

data class FeedEntity(
    val question: String,
    val contents: String,
    val thumnail: ByteArray? = null,
    val viewCount: Int,
    val price: Int
)

fun FeedEntity.toDomain(): Feed {
    return Feed(
        this.question,
        this.contents,
        this.thumnail,
        this.viewCount,
        this.price
    )
}

fun Feed.toData(): FeedEntity {
    return FeedEntity(
        this.question,
        this.contents,
        this.thumnail,
        this.viewCount,
        this.price
    )
}
