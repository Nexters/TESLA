package com.ozcoin.cookiepang.domain.usercategory

import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import javax.inject.Inject

class UserCategoryRepositoryImpl @Inject constructor() : UserCategoryRepository {

    override suspend fun getUserCategory(user: User): DataResult<List<UserCategory>> {
//        return DataResult.OnSuccess(emptyList())
        return DummyUtil.getUserCategoryList()
    }

    override suspend fun setUserInterestIn(user: User, list: List<UserCategory>): Boolean {
        return true
    }

    override suspend fun getAllUserCategory(): DataResult<List<UserCategory>> {
        return DummyUtil.getUserCategoryList()
    }
}
