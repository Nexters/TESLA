package com.ozcoin.cookiepang.domain.user

interface UserRegRepository {

    suspend fun regUser(user: User): Boolean

    suspend fun getRegUser(): User

    fun isUserReg(): Boolean
}
