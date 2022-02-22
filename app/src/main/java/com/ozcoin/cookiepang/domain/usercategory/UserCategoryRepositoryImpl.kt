package com.ozcoin.cookiepang.domain.usercategory

import com.ozcoin.cookiepang.data.category.CategoryRemoteDataSource
import com.ozcoin.cookiepang.data.category.toData
import com.ozcoin.cookiepang.data.category.toDomain
import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserCategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource
) : UserCategoryRepository {

    override suspend fun getUserCategory(userId: String): DataResult<List<UserCategory>> =
        withContext(Dispatchers.IO) {
            DummyUtil.getUserCategoryList()
        }

    override suspend fun setUserInterestIn(user: User, list: List<UserCategory>): Boolean =
        withContext(Dispatchers.IO) {
            var result = false
            kotlin.runCatching {
                val response = categoryRemoteDataSource.setInterestInCategoryList(
                    user.userId.toDataUserId(),
                    list.map { it.toData() }
                )
                if (response is NetworkResult.Success) {
                    result = true
                }
            }

            result
        }

    override suspend fun getAllUserCategory(): DataResult<List<UserCategory>> =
        withContext(Dispatchers.IO) {
            val response = categoryRemoteDataSource.getAllCategoryList()
            val result = if (response is NetworkResult.Success) {
                DataResult.OnSuccess(response.response.map { it.toDomain() })
            } else {
                DataResult.OnFail
            }
            result
        }
}
