package com.ozcoin.cookiepang.data.timeline

import com.ozcoin.cookiepang.data.category.CategoryEntity
import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class TimeLineRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getAllTimeLine(userId: String) =
        safeApiCall { apiService.getAllCookieList(userId, 0, 100) }

    suspend fun getTimeLine(userId: String, categoryEntity: CategoryEntity) =
        safeApiCall {
            apiService.getCookieList(userId, categoryId = categoryEntity.id.toString(), 0, 100)
        }
}
