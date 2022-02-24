package com.ozcoin.cookiepang.data.cookiedetail

import com.ozcoin.cookiepang.data.cookie.CookieStatusType
import com.ozcoin.cookiepang.data.cookie.UpdateCookieInfoRequestParam
import com.ozcoin.cookiepang.data.cookie.toQueryMap
import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class CookieDetailRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun openCookie(userId: String, cookieDetail: CookieDetail) = safeApiCall {
        apiService.updateCookieInfo(
            cookieDetail.cookieId.toString(),
            UpdateCookieInfoRequestParam(
                cookieDetail.hammerPrice,
                CookieStatusType.ACTIVE.name,
                userId.toDataUserId()
            ).toQueryMap()
        )
    }

    suspend fun hideCookie(userId: String, cookieDetail: CookieDetail) = safeApiCall {
        apiService.updateCookieInfo(
            cookieDetail.cookieId.toString(),
            UpdateCookieInfoRequestParam(
                cookieDetail.hammerPrice,
                CookieStatusType.HIDDEN.name,
                userId.toDataUserId()
            ).toQueryMap()
        )
    }

    suspend fun removeCookie(cookieDetail: CookieDetail) = safeApiCall {
        apiService.deleteCookie(cookieDetail.cookieId.toString())
    }

    suspend fun getCookieDetail(userId: String, cookieId: String) =
        safeApiCall { apiService.getCookieDetail(userId.toDataUserId(), cookieId) }
}
