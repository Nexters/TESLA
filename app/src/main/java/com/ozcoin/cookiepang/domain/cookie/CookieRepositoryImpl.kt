package com.ozcoin.cookiepang.domain.cookie

import com.ozcoin.cookiepang.data.cookie.CookieRemoteDataSource
import com.ozcoin.cookiepang.data.cookie.toDomain
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.onboarding.OnBoardingCookie
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.extensions.getDataResult
import com.ozcoin.cookiepang.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CookieRepositoryImpl @Inject constructor(
    private val cookieRemoteDataSource: CookieRemoteDataSource
) : CookieRepository {
    override suspend fun makeOnBoardingCookie(
        userId: String,
        onBoardingCookieList: List<OnBoardingCookie>
    ): Boolean =
        withContext(Dispatchers.IO) {
            var result = false
            getDataResult(
                cookieRemoteDataSource.makeOnBoardingCookie(
                    userId,
                    onBoardingCookieList
                )
            ) {
                result = true
            }
            result
        }

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
        getDataResult(response) {
            purchaseCookieResult = true
        }

        purchaseCookieResult
    }

    override suspend fun getCollectedCookieList(userId: String): DataResult<List<Cookie>> =
        withContext(Dispatchers.IO) {
            val response = cookieRemoteDataSource.getCollectedCookieList(userId)
            getDataResult(response) { res ->
                res.contents.map { it.toDomain() }
            }
        }

    override suspend fun getCreatedCookieList(userId: String): DataResult<List<Cookie>> =
        withContext(Dispatchers.IO) {
            val response = cookieRemoteDataSource.getCreatedCookieList(userId)
            getDataResult(response) { res ->
                res.contents.map { it.toDomain() }
            }
        }
}
