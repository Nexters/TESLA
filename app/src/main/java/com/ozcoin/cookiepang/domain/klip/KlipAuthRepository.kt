package com.ozcoin.cookiepang.domain.klip

interface KlipAuthRepository {

    fun isUserLogin(): Boolean

    suspend fun requestAuth(callbackURL: String?)

    fun saveUserAddress(userAddress: String)

    fun removeUserAddress()

    fun getAuthResult(callback: (Boolean, String?) -> Unit)
}
