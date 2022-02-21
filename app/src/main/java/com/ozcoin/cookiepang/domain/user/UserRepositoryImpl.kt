package com.ozcoin.cookiepang.domain.user

import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.data.user.UserLocalDataSource
import com.ozcoin.cookiepang.data.user.UserRemoteDataSource
import com.ozcoin.cookiepang.data.user.toData
import com.ozcoin.cookiepang.data.user.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    private var loginUser: User? = null

    override suspend fun regUser(user: User): Boolean = withContext(Dispatchers.IO) {
        var regUserResult = false
        val result = userRemoteDataSource.registrationUser(user.toData())
        if (result is NetworkResult.Success) {
            userLocalDataSource.saveUserEntity(result.response)
            loginUser = result.response.toDomain()
            regUserResult = true
        }

        regUserResult
    }

    override suspend fun getLoginUser(): User? = withContext(Dispatchers.IO) {
        if (loginUser == null) {
            Timber.d("is LoginUser null")
            val userEntity = userLocalDataSource.getUserEntity().first()?.let {
                Timber.d("getUserEntity() result: $it")
                val result = userRemoteDataSource.getUser(it.id)
                if (result is NetworkResult.Success) result.response else null
            }
            userEntity?.let { loginUser = it.toDomain() }
        }
        loginUser
    }

    override suspend fun checkDuplicateProfileID(profileID: String): Boolean {
        return true
    }

    override fun logOut() {
        loginUser = null
    }
}
