package com.ozcoin.cookiepang.data.cookie

import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.domain.onboarding.OnBoardingCookie
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class CookieRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun makeACookie(makeACookieRequestParam: MakeACookieRequestParam) = safeApiCall {
        apiService.makeACookie(makeACookieRequestParam)
    }

    suspend fun makeOnBoardingCookie(userId: String, onBoardingCookieList: List<OnBoardingCookie>) =
        safeApiCall {
            val body = CookieOnBoardingRequestBody(
                userId.toDataUserId(),
                onBoardingCookieList.map { it.toData() }.toList()
            )
            apiService.makeOnBoardingCookie(body)
        }

    suspend fun purchaseCookie(cookieId: String, price: Int, purchaserUserId: Int) = safeApiCall {
        apiService.updateCookieInfo(
            cookieId,
            UpdateCookieInfoRequestParam(
                purchaserUserId = purchaserUserId
            ).toQueryMap()
        )
    }

    suspend fun updateCookieInfo(cookieId: String, price: Int, userId: Int) = safeApiCall {
        apiService.updateCookieInfo(
            cookieId,
            UpdateCookieInfoRequestParam(
                price = price
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
