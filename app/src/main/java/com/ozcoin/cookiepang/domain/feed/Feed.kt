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
) : Parcelable {
    companion object {
        private const val ON_LOADING_ID = Int.MIN_VALUE

        fun isLastPage(feed: Feed): Boolean {
            return feed.cookieId != ON_LOADING_ID
        }

        fun typeOnLoading(): Feed {
            return Feed(
                true,
                "",
                "",
                "",
                "",
                "",
                "",
                CookieCardStyle.BLUE,
                0,
                0,
                cookieId = ON_LOADING_ID
            )
        }
    }
}
