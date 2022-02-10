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

    fun prepareRequest(to: String, from: String, value: String, abi: String) {
        klipRequest = ContractTxRequest.Builder()
            .to(to)
            .from(from)
            .value(value)
            .abi(abi)
            .params(ArrayList())
            .build()
    }

    override fun getResult(callback: (Boolean, String?) -> Unit) {
        klip.getResult(
            requestKey,
            object : KlipCallback<KlipResponse> {
                override fun onSuccess(p0: KlipResponse?) {
                    Timber.d("Klip Auth Request Success")
                    p0?.let { res ->
                        requestKey = res.requestKey
                        requestResult = res.status.equals("completed")
                    }
                }

                override fun onFail(p0: KlipErrorResponse?) {
                    Timber.d("Klip Auth Request Fail")
                    requestKey = ""
                }
            }
        )
    }
}
