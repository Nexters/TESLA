package com.ozcoin.cookiepang.data.klip

import android.content.Context
import android.net.Uri
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

    private val bAppInfo = BAppInfo(resourceProvider.getString(R.string.app_name)).apply {
        val successURL = Uri.Builder()
            .scheme(resourceProvider.getString(R.string.scheme_klip_auth))
            .authority(resourceProvider.getString(R.string.host_klip_auth_success))
            .build()
            .toString()

        val failURL = Uri.Builder()
            .scheme(resourceProvider.getString(R.string.scheme_klip_auth))
            .authority(resourceProvider.getString(R.string.host_klip_auth_fail))
            .build()
            .toString()

        callback = BAppDeepLinkCB(successURL, failURL)
    }

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

    abstract fun getResult(callback: (Boolean) -> Unit)

    protected fun prepare() {
        klip.prepare(klipRequest, bAppInfo, prepareCallback)
    }

    fun request() {
        klip.request(requestKey)
    }
}
