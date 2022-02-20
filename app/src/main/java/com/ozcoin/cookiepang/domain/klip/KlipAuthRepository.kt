package com.ozcoin.cookiepang.domain.klip

interface KlipAuthRepository {

    suspend fun requestAuth(callbackURL: String?)

    suspend fun saveUserKlipAddress(userKlipAddress: String)

    suspend fun removeUserKlipAddress()

    suspend fun approveWallet(approve: Boolean)

    fun getAuthResult(callback: (Boolean, String?) -> Unit)
}
