package com.ozcoin.cookiepang.domain.usercategory

import com.ozcoin.cookiepang.data.category.CategoryRemoteDataSource
import com.ozcoin.cookiepang.data.category.toData
import com.ozcoin.cookiepang.data.category.toDomain
import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import javax.inject.Inject

class UserCategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource
) : UserCategoryRepository {

    override suspend fun getUserCategory(userId: String): DataResult<List<UserCategory>> {
        return DummyUtil.getUserCategoryList()
    }

    override suspend fun setUserInterestIn(user: User, list: List<UserCategory>): Boolean {
        var result = false
        kotlin.runCatching {
            val response = categoryRemoteDataSource.setInterestInCategoryList(user.userId.toInt(), list.map { it.toData() })
            if (response is NetworkResult.Success) {
                result = true
            }
        }

        return result
    }

    override suspend fun getAllUserCategory(): DataResult<List<UserCategory>> {
        val response = categoryRemoteDataSource.getAllCategoryList()
        val result = if (response is NetworkResult.Success) {
            DataResult.OnSuccess(response.response.map { it.toDomain() })
        } else {
            DataResult.OnFail
        }
        return result
    }
}
