package com.ozcoin.cookiepang.ui.cookiedetail

import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetailRepository
import com.ozcoin.cookiepang.ui.uistate.UiStateObserver
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CookieDetailViewModel @Inject constructor(
    private val cookieDetailRepository: CookieDetailRepository
) : BaseViewModel() {

    lateinit var uiStateObserver: UiStateObserver

    suspend fun getCookieDetail(cookieId: String): CookieDetail? {
        uiStateObserver.update(UiState.OnLoading)

        val result = cookieDetailRepository.getCookieDetail(cookieId)
        return if (result is DataResult.OnSuccess) {
            Timber.d("getCookieDetail($cookieId) is success")
            uiStateObserver.update(UiState.OnSuccess)

            result.response
        } else {
            Timber.d("getCookieDetail($cookieId) is fail")
            uiStateObserver.update(UiState.OnFail)

            null
        }
    }

    private fun purchaseCookie() {
    }

    fun clickPurchase() {
    }
}
