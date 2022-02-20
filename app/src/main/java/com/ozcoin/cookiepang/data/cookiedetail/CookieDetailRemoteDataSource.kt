package com.ozcoin.cookiepang.data.cookiedetail

import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class CookieDetailRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCookieDetail(userId: String, cookieId: String) =
        safeApiCall { apiService.getCookieDetail(userId, cookieId) }
}
