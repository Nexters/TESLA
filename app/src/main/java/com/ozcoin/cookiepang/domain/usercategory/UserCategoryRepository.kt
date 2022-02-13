package com.ozcoin.cookiepang.domain.usercategory

import com.ozcoin.cookiepang.utils.DataResult

interface UserCategoryRepository {

    suspend fun getUserCategory(): DataResult<List<UserCategory>>
}
