package com.ozcoin.cookiepang.repo.user

import com.ozcoin.cookiepang.data.user.User
import com.ozcoin.cookiepang.data.user.UserRegLocalDataSource

class UserRegRepositoryImpl(
    private val userRegLocalDataSource: UserRegLocalDataSource
): UserRegRepository {

    override suspend fun regUser(user: User): Boolean {
        userRegLocalDataSource.regUser(user)
        return true
    }

    override suspend fun getRegUser(): User {
        return userRegLocalDataSource.getUser()
    }

    override fun isUserReg(): Boolean {
        return userRegLocalDataSource.isUserReg()
    }
}