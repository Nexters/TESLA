package com.ozcoin.cookiepang.data.klip

import android.content.Context
import com.klipwallet.app2app.api.Klip
import com.klipwallet.app2app.api.KlipCallback
import com.klipwallet.app2app.api.request.KlipRequest
import com.klipwallet.app2app.api.request.model.BAppDeepLinkCB
import com.klipwallet.app2app.api.request.model.BAppInfo
import com.klipwallet.app2app.api.response.KlipErrorResponse
import com.klipwallet.app2app.api.response.KlipResponse
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.data.provider.ResourceProvider
import timber.log.Timber

abstract class KlipApi(
    context: Context,
    private val resourceProvider: ResourceProvider
) {
    protected val klip = Klip.getInstance(context)
    protected var requestKey = ""
    protected var requestResult = false

    private val prepareCallback = object : KlipCallback<KlipResponse> {
        override fun onSuccess(p0: KlipResponse?) {
            Timber.d("Klip Request Success")
            p0?.let { res ->
                requestKey = res.requestKey
            }
        }

        override fun onFail(p0: KlipErrorResponse?) {
            Timber.d("Klip Request Fail")
            requestKey = ""
        }
    }

    protected lateinit var klipRequest: KlipRequest

    abstract fun getResult(callback: (Boolean, String?) -> Unit)

    protected fun prepare(callbackURL: String? = null, resultCallback: (Boolean) -> Unit) {
        val bAppInfo = BAppInfo(resourceProvider.getString(R.string.app_name)).apply {
            if (callbackURL != null)
                callback = BAppDeepLinkCB(callbackURL, callbackURL)
        }

        klip.prepare(
            klipRequest,
            bAppInfo,
            object : KlipCallback<KlipResponse> {
                override fun onSuccess(p0: KlipResponse?) {
                    Timber.d("Klip Request Success")
                    p0?.let { res ->
                        requestKey = res.requestKey
                        resultCallback(true)
                    } ?: resultCallback(false)
                }

                override fun onFail(p0: KlipErrorResponse?) {
                    Timber.d("Klip Request Fail")
                    requestKey = ""
                    resultCallback(false)
                }
            }
        )
    }

    fun request() {
        klip.request(requestKey)
    }
}
