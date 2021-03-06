package com.ozcoin.cookiepang.domain.ask

import com.ozcoin.cookiepang.data.ask.AskRemoteDataSource
import com.ozcoin.cookiepang.extensions.getDataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class AskRepositoryImpl @Inject constructor(
    private val askRemoteDataSource: AskRemoteDataSource
) : AskRepository {

    override suspend fun askToUser(ask: Ask): Boolean = withContext(Dispatchers.IO) {
        Timber.d(ask.toString())
        var askResult = false

        getDataResult(askRemoteDataSource.askToUser(ask)) {
            askResult = true
        }

        askResult
    }
}
