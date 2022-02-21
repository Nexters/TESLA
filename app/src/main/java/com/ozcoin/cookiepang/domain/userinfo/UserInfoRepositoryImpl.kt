package com.ozcoin.cookiepang.domain.userinfo

import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor() : UserInfoRepository {

    override suspend fun getUserInfo(userId: String): DataResult<UserInfo> =
        withContext(Dispatchers.IO) {
            delay(500L)
            DummyUtil.getUserInfo(true)
        }

    override suspend fun updateUserInfo(userInfo: UserInfo): Boolean = withContext(Dispatchers.IO) {
        Timber.d("UpdateUserInfo with : $userInfo")
        true
    }
}
