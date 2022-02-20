package com.ozcoin.cookiepang.domain.ask

import com.ozcoin.cookiepang.data.ask.AskRemoteDataSource
import com.ozcoin.cookiepang.data.ask.toData
import com.ozcoin.cookiepang.data.request.NetworkResult
import timber.log.Timber
import javax.inject.Inject

class AskRepositoryImpl @Inject constructor(
    private val askRemoteDataSource: AskRemoteDataSource
) : AskRepository {

    override suspend fun askToUser(ask: Ask): Boolean {
        Timber.d(ask.toString())
        var askResult = false
        val response = askRemoteDataSource.askToUser(ask.toData())
        if (response is NetworkResult.Success) {
            askResult = true
        }

        return askResult
    }
}
