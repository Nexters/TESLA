package com.ozcoin.cookiepang.domain.userinfo

import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor() : UserInfoRepository {

    override suspend fun getUserInfo(userId: String): DataResult<UserInfo> {
        delay(500L)
        return DummyUtil.getUserInfo(true)
    }
}
