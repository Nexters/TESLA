package com.ozcoin.cookiepang.domain.ask

import timber.log.Timber
import javax.inject.Inject

class AskRepositoryImpl @Inject constructor() : AskRepository {

    override suspend fun askToUser(ask: Ask): Boolean {
        Timber.d(ask.toString())
        return true
    }
}
