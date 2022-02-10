package com.ozcoin.cookiepang.domain.user

interface UserRepository {

    suspend fun regUser(user: User): Boolean

    suspend fun getUser(): User

    suspend fun checkDuplicateProfileID(profileID: String): Boolean
}
