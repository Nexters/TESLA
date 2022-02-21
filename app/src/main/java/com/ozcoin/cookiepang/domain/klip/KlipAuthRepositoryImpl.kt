package com.ozcoin.cookiepang.domain.klip

import com.ozcoin.cookiepang.data.klip.KlipAuthDataSource
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class KlipAuthRepositoryImpl @Inject constructor(
    private val klipAuthDataSource: KlipAuthDataSource
) : KlipAuthRepository {

    override suspend fun requestAuth(callbackURL: String?) = withContext(Dispatchers.IO) {
        val response = CompletableDeferred<Boolean>()
        klipAuthDataSource.prepareRequest(callbackURL) {
            response.complete(it)
        }

        if (response.await()) {
            withContext(Dispatchers.Main) {
                klipAuthDataSource.request()
            }
        }
    }

    override fun getAuthResult(callback: (Boolean, String?) -> Unit) {
        klipAuthDataSource.getResult(callback)
    }
}
