package com.ozcoin.cookiepang.domain.klip

interface KlipAuthRepository {

    suspend fun requestAuth(callbackURL: String?)

    fun getAuthResult(callback: (Boolean, String?) -> Unit)
}
