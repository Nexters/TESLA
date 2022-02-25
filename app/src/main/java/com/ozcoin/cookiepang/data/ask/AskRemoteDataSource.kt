package com.ozcoin.cookiepang.data.ask

import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.domain.ask.Ask
import com.ozcoin.cookiepang.domain.ask.AskStatusType
import com.ozcoin.cookiepang.domain.question.Question
import com.ozcoin.cookiepang.domain.user.toDataUserId
import com.ozcoin.cookiepang.extensions.safeApiCall
import timber.log.Timber
import javax.inject.Inject

class AskRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getAskList(userId: String) =
        safeApiCall {
            apiService.getAskList(userId.toDataUserId(), "RECEIVER", 0, 100)
        }

    suspend fun askToUser(ask: Ask) =
        safeApiCall {
            val askEntity = ask.toData()
            Timber.d(askEntity.toString())
            apiService.sendAsk(askEntity)
        }

    suspend fun updateAsk(question: Question, askStatusType: AskStatusType) =
        safeApiCall {
            apiService.updateAsk(
                question.questionId,
                AskUpdateRequestBody(
                    question.question,
                    askStatusType.toData(),
                    question.categoryId
                )
            )
        }
}
