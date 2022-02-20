package com.ozcoin.cookiepang.data.category

import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class CategoryRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getAllCategoryList() = safeApiCall { apiService.getAllCategoryList() }

    fun getCategoryList(userId: Int) {
    }

    suspend fun setInterestInCategoryList(userId: Int, list: List<CategoryEntity>) =
        safeApiCall { apiService.setInterestInCategoryList(userId, list.map { it.id }) }
}
