package com.ozcoin.cookiepang.data.timeline

import com.ozcoin.cookiepang.data.category.toData
import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class TimeLineRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getAllTimeLine(userId: String, page: Int) =
        safeApiCall { apiService.getAllCookieList(userId.toDataUserId(), page, 10) }

    suspend fun getTimeLine(userId: String, userCategory: UserCategory, page: Int) =
        safeApiCall {
            val category = userCategory.toData()
            apiService.getCookieList(userId.toDataUserId(), categoryId = category.id.toString(), page, 10)
        }
}
