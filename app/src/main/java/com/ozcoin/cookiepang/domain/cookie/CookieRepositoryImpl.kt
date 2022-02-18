package com.ozcoin.cookiepang.domain.cookie

import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import javax.inject.Inject

class CookieRepositoryImpl @Inject constructor() : CookieRepository {

    override suspend fun getCollectedCookieList(userId: String): DataResult<List<Cookie>> {
        return DummyUtil.getCollectedCookieList()
    }

    override suspend fun getCreatedCookieList(userId: String): DataResult<List<Cookie>> {
        return DummyUtil.getCreatedCookieList()
    }
}
