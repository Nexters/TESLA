package com.ozcoin.cookiepang.data.user

import com.ozcoin.cookiepang.data.provider.UserPrefProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userPrefProvider: UserPrefProvider
) {

    suspend fun saveUserEntity(user: UserEntity) {
        userPrefProvider.setUserEntity(user)
    }

    fun getUserEntity(): Flow<UserEntity?> {
        return userPrefProvider.getUserEntity()
    }
}
