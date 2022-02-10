package com.ozcoin.cookiepang.data.user

import com.ozcoin.cookiepang.data.provider.SharedPrefProvider
import com.ozcoin.cookiepang.domain.klip.KlipAuthRepository
import javax.inject.Inject

class UserRegLocalDataSource @Inject constructor(
    private val sharedPrefProvider: SharedPrefProvider,
) {
    fun getUser(): UserEntity {
        val userName = sharedPrefProvider.getUserName() ?: "null"
        return UserEntity(userName)
    }

    fun regUser(user: UserEntity) {
        sharedPrefProvider.setUserName(userName = user.name)
    }
}
