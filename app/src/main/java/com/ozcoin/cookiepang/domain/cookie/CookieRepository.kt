package com.ozcoin.cookiepang.domain.cookie

import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.utils.DataResult

interface CookieRepository {

    suspend fun purchaseCookie(purchaserUserId: String, cookieDetail: CookieDetail): Boolean

    suspend fun getCollectedCookieList(userId: String): DataResult<List<Cookie>>

    suspend fun getCreatedCookieList(userId: String): DataResult<List<Cookie>>
}
