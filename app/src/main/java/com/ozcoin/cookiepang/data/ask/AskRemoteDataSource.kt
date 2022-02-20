package com.ozcoin.cookiepang.data.ask

import com.ozcoin.cookiepang.data.request.ApiService
import com.ozcoin.cookiepang.extensions.safeApiCall
import javax.inject.Inject

class AskRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun askToUser(askEntity: AskEntity) = safeApiCall { apiService.sendAsk(askEntity) }
}
