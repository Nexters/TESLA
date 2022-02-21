package com.ozcoin.cookiepang.domain.klip

import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.editcookie.EditCookie

interface KlipContractTxRepository {

    suspend fun requestMakeACookie(editCookie: EditCookie): Boolean

    suspend fun requestBuyACookie(cookieDetail: CookieDetail): Boolean

    suspend fun approveWallet(approve: Boolean): Boolean

    fun getResult(callback: (Boolean, String?) -> Unit)
}
