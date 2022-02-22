package com.ozcoin.cookiepang.domain.cookie

import com.ozcoin.cookiepang.data.cookie.CookieRemoteDataSource
import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CookieRepositoryImpl @Inject constructor(
    private val cookieRemoteDataSource: CookieRemoteDataSource
) : CookieRepository {
    override suspend fun purchaseCookie(
        purchaserUserId: String,
        cookieDetail: CookieDetail
    ): Boolean = withContext(Dispatchers.IO) {
        var purchaseCookieResult = false
        val response = cookieRemoteDataSource.purchaseCookie(
            cookieDetail.cookieId.toString(),
            cookieDetail.hammerPrice,
            purchaserUserId.toDataUserId()
        )
        if (response is NetworkResult.Success)
            purchaseCookieResult = true
        purchaseCookieResult
    }

    override suspend fun getCollectedCookieList(userId: String): DataResult<List<Cookie>> =
        withContext(Dispatchers.IO) {
            delay(1000L)
            DummyUtil.getCollectedCookieList()
        }

    override suspend fun getCreatedCookieList(userId: String): DataResult<List<Cookie>> =
        withContext(Dispatchers.IO) {
            delay(1000L)
            DummyUtil.getCreatedCookieList()
        }
}
