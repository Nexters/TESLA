package com.ozcoin.cookiepang.domain.user

interface UserRepository {

    suspend fun regUser(user: User): Boolean

    suspend fun getLoginUser(): User?

    suspend fun isUserRegistration(walletAddress: String): Boolean

    suspend fun checkDuplicateProfileID(profileID: String): Boolean

    fun logOut()
}
