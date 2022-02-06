package com.ozcoin.cookiepang.data.klip

import android.content.Context
import com.klipwallet.app2app.api.KlipCallback
import com.klipwallet.app2app.api.request.AuthRequest
import com.klipwallet.app2app.api.response.KlipErrorResponse
import com.klipwallet.app2app.api.response.KlipResponse
import com.ozcoin.cookiepang.data.provider.ResourceProvider
import com.ozcoin.cookiepang.data.provider.SharedPrefProvider
import timber.log.Timber

class KlipAuthDataSource(
    context: Context,
    private val resourceProvider: ResourceProvider,
    private val sharedPrefProvider: SharedPrefProvider
) : KlipApi(context, resourceProvider) {

    fun prepareRequest() {
        klipRequest = AuthRequest()
        prepare()
    }

    override fun getResult(callback: (Boolean) -> Unit) {
        klip.getResult(
            requestKey,
            object : KlipCallback<KlipResponse> {
                override fun onSuccess(p0: KlipResponse?) {
                    Timber.d("Klip Auth Request Success")
                    p0?.let { res ->
                        requestKey = res.requestKey
                        requestResult = res.status.equals("completed")
                        if (requestResult) {
                            saveUserAddress(res.result.klaytnAddress)
                            callback(requestResult)
                        }
                    }
                }

                override fun onFail(p0: KlipErrorResponse?) {
                    Timber.d("Klip Auth Request Fail")
                    requestKey = ""
                }
            }
        )
    }

    fun getUserAddress(): String {
        return sharedPrefProvider.getUserAddress() ?: ""
    }

    private fun saveUserAddress(userAddress: String) {
        sharedPrefProvider.setUserAddress(userAddress)
    }

    fun removeUserAddress() {
        sharedPrefProvider.setUserAddress("")
    }
}
