package com.ozcoin.cookiepang.domain.question

import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import kotlinx.coroutines.delay
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor() : QuestionRepository {

    override suspend fun getQuestionList(userId: String): DataResult<List<Question>> {
        delay(500L)
        return DummyUtil.getQuestionList()
    }

    override suspend fun ignoreQuestion(question: Question): Boolean {
        delay(500L)
        return true
    }
}
