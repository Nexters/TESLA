package com.ozcoin.cookiepang.domain.userinfo

import com.ozcoin.cookiepang.data.ask.AskRemoteDataSource
import com.ozcoin.cookiepang.data.cookie.CookieRemoteDataSource
import com.ozcoin.cookiepang.data.request.NetworkResult
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val askRemoteDataSource: AskRemoteDataSource,
    private val cookieRemoteDataSource: CookieRemoteDataSource
) : UserInfoRepository {

    override suspend fun getUserInfo(userId: String): DataResult<UserInfo> =
        withContext(Dispatchers.IO) {
            val user = userRepository.getUser(userId)
                .let { if (it is DataResult.OnSuccess) it.response else null }
            if (user != null) {
                val collectedList = cookieRemoteDataSource.getCollectedCookieList(user.userId).let {
                    if (it is NetworkResult.Success) it.response else null
                }
                val createdList = cookieRemoteDataSource.getCreatedCookieList(user.userId).let {
                    if (it is NetworkResult.Success) it.response else null
                }
                val questions = askRemoteDataSource.getAskList(user.userId).let {
                    if (it is NetworkResult.Success) it.response else emptyList()
                }

                val loginUser = userRepository.getLoginUser()?.userId ?: ""
                val userInfo = UserInfo(
                    isMine = user.userId == loginUser,
                    userId = userId,
                    profileId = user.profileID,
                    introduce = user.introduction,
                    thumbnailUrl = user.profileUrl,
                    profileBackgroundImgUrl = user.backgroundUrl,
                    collectedCnt = collectedList?.totalCount ?: 0,
                    createdCnt = createdList?.totalCount ?: 0,
                    questionCnt = questions.size
                )

                DataResult.OnSuccess(userInfo)
            } else {
                DataResult.OnFail
            }
        }

    override suspend fun updateUserInfo(userInfo: UserInfo): Boolean =
        withContext(Dispatchers.IO) {
            TODO("")
        }
}
