package com.ozcoin.cookiepang.domain.cookiedetail

import com.ozcoin.cookiepang.utils.DataResult

interface CookieDetailRepository {

    suspend fun openCookie(userId: String, cookieDetail: CookieDetail): Boolean

    suspend fun hideCookie(userId: String, cookieDetail: CookieDetail): Boolean

    suspend fun removeCookie(cookieDetail: CookieDetail): Boolean

    suspend fun getCookieDetail(userId: String, cookieId: String): DataResult<CookieDetail>
}
