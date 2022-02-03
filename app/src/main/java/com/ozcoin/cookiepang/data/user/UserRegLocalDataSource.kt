package com.ozcoin.cookiepang.data.user

import com.ozcoin.cookiepang.data.provider.SharedPrefProvider

class UserRegLocalDataSource(
    private val sharedPrefProvider: SharedPrefProvider
) {
    fun getUser(): UserEntity {
        val userName = sharedPrefProvider.getUserName() ?: "null"
        return UserEntity(userName)
    }

    fun regUser(user: UserEntity) {
        sharedPrefProvider.setUserName(userName = user.name)
    }

    fun isUserReg(): Boolean {
        return !sharedPrefProvider.getUserName().isNullOrBlank()
    }
}
