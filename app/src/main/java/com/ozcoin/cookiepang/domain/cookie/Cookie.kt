package com.ozcoin.cookiepang.domain.cookie

data class Cookie(
    val cookieId: String,
    val isHidden: Boolean,
    val cookieBoxImgUrl: String,
    val cookieBoxCoverImgUrl: String,
    val cookieImgUrl: String
)
