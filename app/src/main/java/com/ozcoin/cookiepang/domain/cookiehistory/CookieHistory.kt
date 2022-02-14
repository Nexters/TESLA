package com.ozcoin.cookiepang.domain.cookiehistory

data class CookieHistory(
    val cookieHistoryType: CookieHistoryType,
    val contents: String,
    val timeStamp: String
)
