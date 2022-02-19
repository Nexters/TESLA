package com.ozcoin.cookiepang.domain.user

import com.ozcoin.cookiepang.data.user.UserRegLocalDataSource
import com.ozcoin.cookiepang.data.user.toData
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRegLocalDataSource: UserRegLocalDataSource
) : UserRepository {

    private var loginUser: User? = null
//    private var loginUser: User? = User()

    override suspend fun regUser(user: User): Boolean {
        userRegLocalDataSource.regUser(user.toData())
        this.loginUser = user
        return true
    }

    override suspend fun getLoginUser(): User? {
//        return userRegLocalDataSource.getUser().toDomain()
        return loginUser
    }

    override suspend fun checkDuplicateProfileID(profileID: String): Boolean {
        return true
    }

    override fun logOut() {
        loginUser = null
    }
}
