package com.ozcoin.cookiepang.domain.klip

import com.ozcoin.cookiepang.data.klip.KlipAuthDataSource

class KlipAuthRepositoryImpl(
    private val klipAuthDataSource: KlipAuthDataSource
) : KlipAuthRepository {

    override fun isUserLogin(): Boolean {
        return klipAuthDataSource.getUserAddress().isNotEmpty()
    }

    override fun prepareRequest() {
        klipAuthDataSource.prepareRequest()
    }

    override fun requestAuth() {
        klipAuthDataSource.request()
    }

    override fun getAuthResult(callback: (Boolean) -> Unit) {
        klipAuthDataSource.getResult(callback)
    }

    override fun logOut() {
        klipAuthDataSource.removeUserAddress()
    }
}
