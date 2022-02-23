package com.ozcoin.cookiepang.domain.user

import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.data.user.UserEntity
import com.ozcoin.cookiepang.data.user.UserLocalDataSource
import com.ozcoin.cookiepang.data.user.UserRemoteDataSource
import com.ozcoin.cookiepang.data.user.toData
import com.ozcoin.cookiepang.data.user.toDomain
import com.ozcoin.cookiepang.extensions.getDataResult
import com.ozcoin.cookiepang.utils.DataResult
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

    override suspend fun getUser(userId: String): DataResult<User> =
        withContext(Dispatchers.IO) {
            getDataResult(userRemoteDataSource.getUser(userId.toDataUserId())) {
                it.toDomain()
            }
        }

    override suspend fun regUser(user: User): Boolean =
        withContext(Dispatchers.IO) {
            var regUserResult = false
            getDataResult(userRemoteDataSource.registrationUser(user.toData())) { res ->
                regUserResult = true
                userLocalDataSource.saveUserEntity(res)
                loginUser = res.toDomain()
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
            userEntity?.let {
                userLocalDataSource.saveUserEntity(it)
                loginUser = it.toDomain()
            }
        }
        loginUser
    }

    override suspend fun updateUser(user: User): Boolean =
        withContext(Dispatchers.IO) {
            var updateUserResult = false
            user.updateProfileBackgroundImg = null
            user.updateThumbnailImg = null
            getDataResult(userRemoteDataSource.updateUserEntity(user)) {
                loginUser = it.toDomain()
                updateUserResult = true
            }
            updateUserResult
        }

    override suspend fun isUserRegistration(walletAddress: String): Boolean =
        withContext(Dispatchers.IO) {
            var userRegistrationResult = false
            getDataResult(userRemoteDataSource.isUserRegistration(walletAddress)) { res ->
                getDataResult(userRemoteDataSource.getUser(res.userId)) { user ->
                    userLocalDataSource.saveUserEntity(user)
                    loginUser = user.toDomain()
                    userRegistrationResult = true
                }
            }

            userRegistrationResult
        }

    override suspend fun checkDuplicateProfileID(profileID: String): Boolean {
        return true
    }

    override suspend fun logOut() {
        userLocalDataSource.saveUserEntity(UserEntity.empty())
        loginUser = null
    }
}
