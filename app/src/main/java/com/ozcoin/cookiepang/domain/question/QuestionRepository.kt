package com.ozcoin.cookiepang.domain.question

import com.ozcoin.cookiepang.utils.DataResult

interface QuestionRepository {

    suspend fun getQuestionList(userId: String): DataResult<List<Question>>

    suspend fun ignoreQuestion(question: Question): Boolean
}
