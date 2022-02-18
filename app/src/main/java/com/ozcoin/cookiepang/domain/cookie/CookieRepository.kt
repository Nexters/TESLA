package com.ozcoin.cookiepang.domain.cookie

import com.ozcoin.cookiepang.utils.DataResult

interface CookieRepository {

    suspend fun getCollectedCookieList(userId: String): DataResult<List<Cookie>>

    suspend fun getCreatedCookieList(userId: String): DataResult<List<Cookie>>
}
