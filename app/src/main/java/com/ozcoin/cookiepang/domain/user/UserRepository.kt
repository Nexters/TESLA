package com.ozcoin.cookiepang.domain.user

import com.ozcoin.cookiepang.utils.DataResult

interface UserRepository {

    suspend fun getUser(userId: String): DataResult<User>

    suspend fun regUser(user: User): Boolean

    suspend fun getLoginUser(): User?

    suspend fun updateUser(user: User): Boolean

    suspend fun isUserRegistration(walletAddress: String): Boolean

    suspend fun checkDuplicateProfileID(profileID: String): Boolean

    suspend fun logOut()
}
