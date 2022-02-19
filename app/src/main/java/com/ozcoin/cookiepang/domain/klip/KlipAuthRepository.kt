package com.ozcoin.cookiepang.domain.klip

import kotlinx.coroutines.flow.Flow

interface KlipAuthRepository {

    fun isUserLogin(): Flow<Boolean>

    suspend fun requestAuth(callbackURL: String?)

    suspend fun saveUserKlipAddress(userKlipAddress: String)

    suspend fun removeUserKlipAddress()

    suspend fun approveWallet(approve: Boolean)

    fun getAuthResult(callback: (Boolean, String?) -> Unit)
}
