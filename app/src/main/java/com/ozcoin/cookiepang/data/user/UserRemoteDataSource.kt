package com.ozcoin.cookiepang.data.user

import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun registrationUser(userEntity: UserEntity): NetworkResult<UserEntity> {
        return safeApiCall { apiService.registrationUser(userEntity) }
    }

    suspend fun getUser(userId: Int): NetworkResult<UserEntity> {
        return safeApiCall { apiService.getUser(userId) }
    }

    suspend fun isUserRegistration(walletAddress: String) = safeApiCall {
        apiService.login(LoginRequestParam(walletAddress))
    }
}
