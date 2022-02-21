package com.ozcoin.cookiepang.domain.question

import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DummyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor() : QuestionRepository {

    override suspend fun getQuestionList(userId: String): DataResult<List<Question>> =
        withContext(Dispatchers.IO) {
            delay(500L)
            DummyUtil.getQuestionList()
        }

    override suspend fun ignoreQuestion(question: Question): Boolean = withContext(Dispatchers.IO) {
        delay(500L)
        true
    }
}
