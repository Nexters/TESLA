package com.ozcoin.cookiepang.domain.usercategory

import com.ozcoin.cookiepang.utils.DataResult
import javax.inject.Inject

class UserCategoryRepositoryImpl @Inject constructor() : UserCategoryRepository {

    override suspend fun getUserCategory(): DataResult<List<UserCategory>> {
//        return DataResult.OnSuccess(emptyList())
        return DataResult.OnSuccess(
            listOf(
                UserCategory("Free Chat", false),
                UserCategory("Money", false),
                UserCategory("Friend", false),
                UserCategory("Hobby", false),
            )
        )
    }
}
