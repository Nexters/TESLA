package com.ozcoin.cookiepang.domain.user

import com.ozcoin.cookiepang.data.user.UserRegLocalDataSource
import com.ozcoin.cookiepang.data.user.toData
import com.ozcoin.cookiepang.data.user.toDomain
import javax.inject.Inject

class UserRegRepositoryImpl @Inject constructor(
    private val userRegLocalDataSource: UserRegLocalDataSource
) : UserRegRepository {

    override suspend fun regUser(user: User): Boolean {
        userRegLocalDataSource.regUser(user.toData())
        return true
    }

    override suspend fun getRegUser(): User {
        return userRegLocalDataSource.getUser().toDomain()
    }

    override fun isUserReg(): Boolean {
        return userRegLocalDataSource.isUserReg()
    }
}
