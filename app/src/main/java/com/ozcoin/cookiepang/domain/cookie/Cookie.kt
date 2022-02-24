package com.ozcoin.cookiepang.domain.cookie

import com.ozcoin.cookiepang.domain.feed.CookieCardStyle

data class Cookie(
    val cookieId: String,
    val isHidden: Boolean,
    var cookieCardStyle: CookieCardStyle,
    val cookieBoxImgUrl: String?,
    val cookieBoxCoverImgUrl: String?,
    val cookieImgUrl: String?,
    val ownedUserId: String
)
