package com.ozcoin.cookiepang.domain.cookiedetail

import com.ozcoin.cookiepang.data.cookiedetail.CookieDetailRemoteDataSource
import com.ozcoin.cookiepang.data.cookiedetail.toDomain
import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.utils.DataResult
import javax.inject.Inject

class CookieDetailRepositoryImpl @Inject constructor(
    private val cookieDetailRemoteDataSource: CookieDetailRemoteDataSource
) : CookieDetailRepository {

    override suspend fun getCookieDetail(
        userId: String,
        cookieId: String
    ): DataResult<CookieDetail> {
        val response = cookieDetailRemoteDataSource.getCookieDetail(userId, cookieId)
        return if (response is NetworkResult.Success) {
            DataResult.OnSuccess(response.response.toDomain())
        } else {
            DataResult.OnFail
        }
    }
}
