package com.ozcoin.cookiepang.domain.cookiedetail

import com.ozcoin.cookiepang.utils.DataResult

interface CookieDetailRepository {

    suspend fun getCookieDetail(userId: String, cookieId: String): DataResult<CookieDetail>
}
