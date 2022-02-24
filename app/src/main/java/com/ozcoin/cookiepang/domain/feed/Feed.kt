package com.ozcoin.cookiepang.domain.feed

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Feed(
    val isHidden: Boolean,
    val userThumbnailUrl: String,
    val feedUserId: String,
    val userProfileId: String,
    val createdTimeStamp: String,
    val question: String,
    val answer: String?,
    val cookieCardStyle: CookieCardStyle,
    val viewCount: Int,
    val hammerPrice: Int,
    val cookieId: Int
) : Parcelable
