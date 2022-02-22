package com.ozcoin.cookiepang.domain.cookiedetail

import com.ozcoin.cookiepang.data.cookiedetail.CookieDetailRemoteDataSource
import com.ozcoin.cookiepang.data.cookiedetail.toDomain
import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class CookieDetailRepositoryImpl @Inject constructor(
    private val cookieDetailRemoteDataSource: CookieDetailRemoteDataSource
) : CookieDetailRepository {
    override suspend fun openCookie(userId: String, cookieDetail: CookieDetail): Boolean =
        withContext(Dispatchers.IO) {
            var openCookieResult = false
            val response = cookieDetailRemoteDataSource.openCookie(userId, cookieDetail)
            if (response is NetworkResult.Success)
                openCookieResult = true
            openCookieResult.also { Timber.d("openCookie($it)") }
        }

    override suspend fun hideCookie(userId: String, cookieDetail: CookieDetail): Boolean =
        withContext(Dispatchers.IO) {
            var hideCookieResult = false
            val response = cookieDetailRemoteDataSource.hideCookie(userId, cookieDetail)
            if (response is NetworkResult.Success)
                hideCookieResult = true
            hideCookieResult.also { Timber.d("hideCookie($it)") }
        }

    override suspend fun removeCookie(cookieDetail: CookieDetail): Boolean =
        withContext(Dispatchers.IO) {
            var removeCookieResult = false
            val response = cookieDetailRemoteDataSource.removeCookie(cookieDetail)
            if (response is NetworkResult.Success)
                removeCookieResult = true
            removeCookieResult.also { Timber.d("removeCookie($it)") }
        }

    override suspend fun getCookieDetail(
        userId: String,
        cookieId: String
    ): DataResult<CookieDetail> = withContext(Dispatchers.IO) {
        val response = cookieDetailRemoteDataSource.getCookieDetail(userId, cookieId)
        if (response is NetworkResult.Success) {
            DataResult.OnSuccess(response.response.toDomain())
        } else {
            DataResult.OnFail
        }
    }
}
