package com.ozcoin.cookiepang.domain.cookiedetail

import com.ozcoin.cookiepang.utils.DataResult

interface CookieDetailRepository {

    suspend fun getCookieDetail(cookieId: String): DataResult<CookieDetail>
}
