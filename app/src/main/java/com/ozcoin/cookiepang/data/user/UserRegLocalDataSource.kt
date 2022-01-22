package com.ozcoin.cookiepang.data.user

import com.ozcoin.cookiepang.provider.SharedPrefProvider

class UserRegLocalDataSource(
    private val sharedPrefProvider: SharedPrefProvider
) {

    fun getUser(): User {
        val userName = sharedPrefProvider.getUserName() ?: "null"
        return User(userName)
    }

    fun regUser(user: User) {
        sharedPrefProvider.setUserName(userName = user.name)
    }

    fun isUserReg(): Boolean {
        return !sharedPrefProvider.getUserName().isNullOrBlank()
    }

}