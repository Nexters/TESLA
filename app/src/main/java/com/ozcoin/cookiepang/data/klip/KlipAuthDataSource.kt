package com.ozcoin.cookiepang.data.klip

import android.content.Context
import com.klipwallet.app2app.api.KlipCallback
import com.klipwallet.app2app.api.request.AuthRequest
import com.klipwallet.app2app.api.response.KlipErrorResponse
import com.klipwallet.app2app.api.response.KlipResponse
import com.ozcoin.cookiepang.data.provider.ResourceProvider
import timber.log.Timber
import javax.inject.Inject

class KlipAuthDataSource @Inject constructor(
    context: Context,
    private val resourceProvider: ResourceProvider
) : KlipApi(context, resourceProvider) {

    fun prepareRequest(callbackURL: String?, resultCallback: (Boolean) -> Unit) {
        klipRequest = AuthRequest()
        prepare(callbackURL, resultCallback)
    }

    override fun getResult(callback: (Boolean, String?) -> Unit) {
        if (requestKey.isNotEmpty()) {
            klip.getResult(
                requestKey,
                object : KlipCallback<KlipResponse> {
                    override fun onSuccess(p0: KlipResponse?) {
                        Timber.d("Klip Auth Request Success")
                        p0?.let { res ->
                            requestResult = res.status.equals("completed")

                            if (requestResult) {
                                requestKey = ""
                                callback(requestResult, res.result.klaytnAddress)
                            } else {
                                callback(requestResult, null)
                            }
                        }
                    }

                    override fun onFail(p0: KlipErrorResponse?) {
                        Timber.d("Klip Auth Request Fail")
                        requestKey = ""

                        callback(requestResult, null)
                    }
                }
            )
        }
    }
}
