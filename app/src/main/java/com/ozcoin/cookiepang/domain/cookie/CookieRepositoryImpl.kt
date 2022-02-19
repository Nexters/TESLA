package com.ozcoin.cookiepang.domain.cookie

import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import kotlinx.coroutines.delay
import javax.inject.Inject

class CookieRepositoryImpl @Inject constructor() : CookieRepository {

    override suspend fun getCollectedCookieList(userId: String): DataResult<List<Cookie>> {
        delay(1000L)
        return DummyUtil.getCollectedCookieList()
    }

    override suspend fun getCreatedCookieList(userId: String): DataResult<List<Cookie>> {
        delay(1000L)
        return DummyUtil.getCreatedCookieList()
    }
}
