package com.ozcoin.cookiepang.domain.klip

interface KlipTokenTxRepository {

    fun reqSendToken(sendToken: SendToken, callback: (Boolean) -> Unit)
}
