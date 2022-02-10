package com.ozcoin.cookiepang.domain.user

import com.ozcoin.cookiepang.data.user.UserRegLocalDataSource
import com.ozcoin.cookiepang.data.user.toData
import com.ozcoin.cookiepang.data.user.toDomain
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRegLocalDataSource: UserRegLocalDataSource
) : UserRepository {

    private val user = User()

    override suspend fun regUser(user: User): Boolean {
        userRegLocalDataSource.regUser(user.toData())
        return true
    }

    override suspend fun getUser(): User {
        return userRegLocalDataSource.getUser().toDomain()
    }

    override suspend fun checkDuplicateProfileID(profileID: String): Boolean {
        return true
    }
}
