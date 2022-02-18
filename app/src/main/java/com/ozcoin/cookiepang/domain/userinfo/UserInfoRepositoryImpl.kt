package com.ozcoin.cookiepang.domain.userinfo

import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor() : UserInfoRepository {

    override suspend fun getUserInfo(userId: String): DataResult<UserInfo> {
        return DummyUtil.getUserInfo(true)
    }
}
