package com.ozcoin.cookiepang.data.cookie

import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class CookieRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun makeACookie(makeACookieRequestParam: MakeACookieRequestParam) = safeApiCall {
        apiService.makeACookie(makeACookieRequestParam)
    }

    suspend fun purchaseCookie(cookieId: String, price: Int, purchaserUserId: Int) = safeApiCall {
        apiService.updateCookieInfo(
            cookieId,
            UpdateCookieInfoRequestParam(
                price, CookieStatusType.ACTIVE.name, purchaserUserId
            ).toQueryMap()
        )
    }

    suspend fun updateCookieInfo(cookieId: String, price: Int, userId: Int) = safeApiCall {
        apiService.updateCookieInfo(
            cookieId,
            UpdateCookieInfoRequestParam(
                price, CookieStatusType.ACTIVE.name, userId
            ).toQueryMap()
        )
    }

    suspend fun getCollectedCookieList(userId: String) = safeApiCall {
        apiService.getUserCookieList(
            userId.toDataUserId(),
            CookieQueryTargetType.OWNED.name,
            0,
            100
        )
    }

    suspend fun getCreatedCookieList(userId: String) = safeApiCall {
        apiService.getUserCookieList(
            userId.toDataUserId(),
            CookieQueryTargetType.AUTHOR.name,
            0,
            100
        )
    }
}
