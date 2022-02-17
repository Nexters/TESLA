package com.ozcoin.cookiepang.domain.usercategory

import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import javax.inject.Inject

class UserCategoryRepositoryImpl @Inject constructor() : UserCategoryRepository {

    override suspend fun getUserCategory(): DataResult<List<UserCategory>> {
//        return DataResult.OnSuccess(emptyList())
        return DummyUtil.getUserCategoryList()
    }
}
