package com.ozcoin.cookiepang.repo.user

import com.ozcoin.cookiepang.data.user.User

interface UserRegRepository {

    suspend fun regUser(user: User) : Boolean

    suspend fun getRegUser() : User

    fun isUserReg() : Boolean

}