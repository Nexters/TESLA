package com.ozcoin.cookiepang.domain.cookie

import com.ozcoin.cookiepang.data.cookie.CookieRemoteDataSource
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import kotlinx.coroutines.delay
import javax.inject.Inject

class CookieRepositoryImpl @Inject constructor(
    private val cookieRemoteDataSource: CookieRemoteDataSource
) : CookieRepository {
    override suspend fun purchaseCookie(purchaserUserId: String, cookieDetail: CookieDetail): Boolean {
        cookieRemoteDataSource.purchaseCookie(cookieDetail.cookieId.toString(), cookieDetail.hammerPrice, purchaserUserId.toInt())
        return true
    }

    override suspend fun getCollectedCookieList(userId: String): DataResult<List<Cookie>> {
        delay(1000L)
        return DummyUtil.getCollectedCookieList()
    }

    override suspend fun getCreatedCookieList(userId: String): DataResult<List<Cookie>> {
        delay(1000L)
        return DummyUtil.getCreatedCookieList()
    }
}
