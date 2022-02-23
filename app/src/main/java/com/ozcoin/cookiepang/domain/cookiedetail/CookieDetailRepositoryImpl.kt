package com.ozcoin.cookiepang.domain.cookiedetail

import com.ozcoin.cookiepang.data.cookiedetail.CookieDetailRemoteDataSource
import com.ozcoin.cookiepang.data.cookiedetail.toDomain
import com.ozcoin.cookiepang.extensions.getDataResult
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
            getDataResult(cookieDetailRemoteDataSource.openCookie(userId, cookieDetail)) {
                openCookieResult = true
            }

            openCookieResult.also { Timber.d("openCookie($it)") }
        }

    override suspend fun hideCookie(userId: String, cookieDetail: CookieDetail): Boolean =
        withContext(Dispatchers.IO) {
            var hideCookieResult = false
            getDataResult(cookieDetailRemoteDataSource.hideCookie(userId, cookieDetail)) {
                hideCookieResult = true
            }

            hideCookieResult.also { Timber.d("hideCookie($it)") }
        }

    override suspend fun removeCookie(cookieDetail: CookieDetail): Boolean =
        withContext(Dispatchers.IO) {
            var removeCookieResult = false
            getDataResult(cookieDetailRemoteDataSource.removeCookie(cookieDetail)) {
                removeCookieResult = true
            }

            removeCookieResult.also { Timber.d("removeCookie($it)") }
        }

    override suspend fun getCookieDetail(
        userId: String,
        cookieId: String
    ): DataResult<CookieDetail> = withContext(Dispatchers.IO) {
        getDataResult(cookieDetailRemoteDataSource.getCookieDetail(userId, cookieId)) {
            it.toDomain()
        }
    }
}
