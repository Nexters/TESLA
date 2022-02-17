package com.ozcoin.cookiepang.domain.user

interface UserRepository {

    suspend fun regUser(user: User): Boolean

    suspend fun getLoginUser(): User?

    suspend fun checkDuplicateProfileID(profileID: String): Boolean
}
