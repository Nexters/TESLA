package com.ozcoin.cookiepang.data.cookie

import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class CookieRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun makeACookie(makeACookieRequestParam: MakeACookieRequestParam) =
        safeApiCall { apiService.makeACookie(makeACookieRequestParam) }

    suspend fun purchaseCookie(cookieId: String, price: Int, purchaserUserId: Int) =
        safeApiCall {
            apiService.updateCookieInfo(
                cookieId,
                UpdateCookieInfoRequestParam(
                    price, CookieStatusType.ACTIVE.name, purchaserUserId
                )
            )
        }

    suspend fun updateCookieInfo(cookieId: String, price: Int, userId: Int) =
        safeApiCall {
            apiService.updateCookieInfo(
                cookieId,
                UpdateCookieInfoRequestParam(
                    price, CookieStatusType.ACTIVE.name, userId
                )
            )
        }
}