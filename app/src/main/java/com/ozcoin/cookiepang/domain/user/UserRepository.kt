package com.ozcoin.cookiepang.domain.user

import com.ozcoin.cookiepang.domain.question.Question
import com.ozcoin.cookiepang.utils.DataResult

interface UserRepository {

    suspend fun regUser(user: User): Boolean

    suspend fun getLoginUser(): User?

    suspend fun checkDuplicateProfileID(profileID: String): Boolean

    suspend fun getQuestionList(userId: String): DataResult<List<Question>>
}
