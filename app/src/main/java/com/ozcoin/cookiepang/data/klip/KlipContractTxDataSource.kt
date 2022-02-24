package com.ozcoin.cookiepang.data.klip

import android.content.Context
import com.klipwallet.app2app.api.KlipCallback
import com.klipwallet.app2app.api.request.ContractTxRequest
import com.klipwallet.app2app.api.response.KlipErrorResponse
import com.klipwallet.app2app.api.response.KlipResponse
import com.ozcoin.cookiepang.data.provider.ResourceProvider
import timber.log.Timber

class KlipContractTxDataSource(
    context: Context,
    private val resourceProvider: ResourceProvider
) : KlipApi(context, resourceProvider) {

    fun prepareRequest(to: String, from: String, value: String, abi: String, params: ArrayList<Any>, resultCallback: (Boolean) -> Unit) {
        klipRequest = ContractTxRequest.Builder()
            .to(to)
            .from(from)
            .value(value)
            .abi(abi)
            .params(params)
            .build()
        prepare(null, resultCallback)
    }

    override fun getResult(callback: (Boolean, String?) -> Unit) {
        if (requestKey.isNotEmpty()) {
            klip.getResult(
                requestKey,
                object : KlipCallback<KlipResponse> {
                    override fun onSuccess(p0: KlipResponse?) {
                        Timber.d("Klip Request Success")
                        Timber.d(p0?.toString())
                        p0?.let { res ->
                            if (res.result != null) {
                                requestResult = res.result.txHash.isNotBlank()
                            }

                            if (requestResult) {
                                requestKey = ""
                                callback(requestResult, res.result.txHash)
                            } else {
                                callback(requestResult, null)
                            }
                        }
                    }

                    override fun onFail(p0: KlipErrorResponse?) {
                        Timber.d("Klip Request Fail")
                        Timber.d(p0?.toString())
                        callback(requestResult, null)
                        requestKey = ""
                    }
                }
            )
        }
    }
}
