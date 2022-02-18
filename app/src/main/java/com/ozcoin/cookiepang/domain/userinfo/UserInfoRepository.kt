package com.ozcoin.cookiepang.domain.userinfo

import com.ozcoin.cookiepang.utils.DataResult

interface UserInfoRepository {

    suspend fun getUserInfo(userId: String): DataResult<UserInfo>
}
