package com.ozcoin.cookiepang.domain.klip

import com.ozcoin.cookiepang.data.klip.KlipTokenTxDataSource

class KlipTokenTxRepositoryImpl(
    private val klipTokenTxDataSource: KlipTokenTxDataSource
) : KlipTokenTxRepository {

    override fun reqSendToken(sendToken: SendToken, callback: (Boolean) -> Unit) {
        klipTokenTxDataSource.prepareRequest(
            sendToken.tokenType,
            sendToken.to,
            sendToken.from,
            sendToken.amount
        )
        klipTokenTxDataSource.request()
        klipTokenTxDataSource.getResult(callback)
    }
}
