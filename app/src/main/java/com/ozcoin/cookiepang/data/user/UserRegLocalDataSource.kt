package com.ozcoin.cookiepang.data.user

import com.ozcoin.cookiepang.data.provider.UserPrefProvider
import javax.inject.Inject

class UserRegLocalDataSource @Inject constructor(
    private val userPrefProvider: UserPrefProvider
) {
    fun getUser(): UserEntity {
        return UserEntity("userName")
    }

    fun regUser(user: UserEntity) {
    }
}
