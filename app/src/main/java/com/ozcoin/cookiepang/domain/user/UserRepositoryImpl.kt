package com.ozcoin.cookiepang.domain.user

import com.ozcoin.cookiepang.data.user.UserRegLocalDataSource
import com.ozcoin.cookiepang.data.user.toData
import com.ozcoin.cookiepang.domain.question.Question
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRegLocalDataSource: UserRegLocalDataSource
) : UserRepository {

//    private var user: User? = null
    private var user: User? = User()

    override suspend fun regUser(user: User): Boolean {
        userRegLocalDataSource.regUser(user.toData())
        this.user = user
        return true
    }

    override suspend fun getLoginUser(): User? {
//        return userRegLocalDataSource.getUser().toDomain()
        return DummyUtil.getLoginUser()
    }

    override suspend fun checkDuplicateProfileID(profileID: String): Boolean {
        return true
    }

    override suspend fun getQuestionList(userId: String): DataResult<List<Question>> {
        return DummyUtil.getQuestionList(true)
    }
}
