package com.ozcoin.cookiepang.data.notification

import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class NotificationRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getNotificationList(userId: String, page: Int = 0, size: Int = 3) =
        safeApiCall {
            apiService.getAlarmList(userId.toDataUserId(), 0, 100)
        }
}
