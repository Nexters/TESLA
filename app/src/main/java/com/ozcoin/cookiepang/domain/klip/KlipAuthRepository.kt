package com.ozcoin.cookiepang.domain.klip

interface KlipAuthRepository {

    fun isUserLogin(): Boolean

    fun prepareRequest()

    fun requestAuth()

    fun getAuthResult(callback: (Boolean) -> Unit)

    fun logOut()
}
