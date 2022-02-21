package com.ozcoin.cookiepang.domain.klip

import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.editcookie.EditCookie

interface KlipContractTxRepository {

    suspend fun requestMakeACookie(editCookie: EditCookie)

    suspend fun requestBuyACookie(cookieDetail: CookieDetail)

    suspend fun approveWallet(approve: Boolean)

    fun getResult(callback: (Boolean, String?) -> Unit)
}
