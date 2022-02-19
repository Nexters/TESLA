package com.ozcoin.cookiepang.domain.klip

import com.ozcoin.cookiepang.data.klip.KlipAuthDataSource
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class KlipAuthRepositoryImpl @Inject constructor(
    private val klipAuthDataSource: KlipAuthDataSource
) : KlipAuthRepository {

    override fun isUserLogin(): Flow<Boolean> {
        return klipAuthDataSource.getUserKlipAddress().map {
            it.isNotEmpty()
        }
    }

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

    override suspend fun saveUserKlipAddress(userKlipAddress: String) {
        klipAuthDataSource.saveUserKlipAddress(userKlipAddress)
    }

    override suspend fun removeUserKlipAddress() {
        klipAuthDataSource.removeUserKlipAddress()
    }

    override suspend fun approveWallet(approve: Boolean) {
    }

    override fun getAuthResult(callback: (Boolean, String?) -> Unit) {
        klipAuthDataSource.getResult(callback)
    }
}
