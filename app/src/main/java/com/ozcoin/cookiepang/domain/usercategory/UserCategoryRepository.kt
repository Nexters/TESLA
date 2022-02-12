package com.ozcoin.cookiepang.domain.usercategory

interface UserCategoryRepository {

    suspend fun getUserCategory(): List<UserCategory>
}
