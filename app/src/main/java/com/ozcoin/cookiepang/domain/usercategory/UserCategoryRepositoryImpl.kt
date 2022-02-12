package com.ozcoin.cookiepang.domain.usercategory

import javax.inject.Inject

class UserCategoryRepositoryImpl @Inject constructor() : UserCategoryRepository {

    override suspend fun getUserCategory(): List<UserCategory> {
        return emptyList()
    }
}
