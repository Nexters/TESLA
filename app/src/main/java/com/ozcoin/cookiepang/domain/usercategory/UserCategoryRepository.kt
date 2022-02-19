package com.ozcoin.cookiepang.domain.usercategory

import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.utils.DataResult

interface UserCategoryRepository {

    suspend fun getAllUserCategory(): DataResult<List<UserCategory>>

    suspend fun getUserCategory(userId: String): DataResult<List<UserCategory>>

    suspend fun setUserInterestIn(user: User, list: List<UserCategory>): Boolean
}
