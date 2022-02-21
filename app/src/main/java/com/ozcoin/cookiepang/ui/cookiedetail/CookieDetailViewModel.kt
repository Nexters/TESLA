package com.ozcoin.cookiepang.ui.cookiedetail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.cookie.CookieRepository
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetailRepository
import com.ozcoin.cookiepang.domain.cookiedetail.toEditCookie
import com.ozcoin.cookiepang.domain.klip.KlipContractTxRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DialogUtil
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.TitleClickListener
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.EventObserver
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CookieDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cookieRepository: CookieRepository,
    private val cookieDetailRepository: CookieDetailRepository,
    private val klipContractTxRepository: KlipContractTxRepository
) : BaseViewModel(), LifecycleEventObserver {

    private val _cookieDetail = MutableStateFlow<CookieDetail?>(null)
    val cookieDetail: StateFlow<CookieDetail?>
        get() = _cookieDetail

    val titleClickListener = TitleClickListener(
        EventObserver {
            viewModelScope.launch { _eventFlow.emit(it) }
        }
    )

    lateinit var eventObserver: EventObserver
    lateinit var uiStateObserver: UiStateObserver

    private lateinit var cookieId: String

    fun getCookieDetail(cookieId: String) {
        if (cookieId.isNotEmpty()) {
            this.cookieId = cookieId
            uiStateObserver.update(UiState.OnLoading)

            viewModelScope.launch {
                val result = userRepository.getLoginUser()?.let { cookieDetailRepository.getCookieDetail(it.userId, cookieId) }
                if (result is DataResult.OnSuccess) {
                    Timber.d("getCookieDetail($cookieId) is success")
                    uiStateObserver.update(UiState.OnSuccess)
                    _cookieDetail.emit(result.response)
                } else {
                    Timber.d("getCookieDetail($cookieId) is fail")
                    uiStateObserver.update(UiState.OnFail)
                    navigateUp()
                }
            }
        } else {
            navigateUp()
        }
    }

    private fun purchaseCookie() {
        Timber.d("purchaseCookie() called")
        uiStateObserver.update(UiState.OnLoading)

        viewModelScope.launch {
            if (cookieDetail.value != null && userRepository.getLoginUser() != null) {
                if (!klipContractTxRepository.requestBuyACookie(cookieDetail.value!!))
                    uiStateObserver.update(UiState.OnFail)
            } else {
                Timber.d("cookieDetail or loginUser is null")
                uiStateObserver.update(UiState.OnFail)
            }
        }
    }

    private fun showPurchaseCookieSuccessDialog() {
        eventObserver.update(
            Event.ShowDialog(
                DialogUtil.getPurchaseCookieSuccessContents(),
                callback = {
                    if (it) getCookieDetail(cookieId) else Timber.d("PurchaseCookieSuccessDialog cancelled")
                }
            )
        )
    }

    private fun showPurchaseCookieDialog() {
        eventObserver.update(
            Event.ShowDialog(
                DialogUtil.getConfirmPurchaseCookieContents(cookieDetail.value),
                callback = {
                    if (it) purchaseCookie() else Timber.d("ConfirmPurchaseCookieDialog cancelled")
                }
            )
        )
    }

    private fun hideCookie() {
    }

    private fun openCookie() {
    }

    private fun deleteCookie() {
    }

    private fun showDeleteCookieDialog() {
        eventObserver.update(
            Event.ShowDialog(
                DialogUtil.getDeleteCookieContents(),
                callback = {
                    Timber.d("DeleteCookieDialog result($it)")
                    if (it) deleteCookie()
                }
            )
        )
    }

    private fun editPricingInfo() {
        eventObserver.update(
            Event.Nav.ToEditCookie(cookieDetail.value?.toEditCookie())
        )
    }

    fun clickCookieContentsBtn(isMine: Boolean) {
        if (isMine) {
            editPricingInfo()
        } else {
            showPurchaseCookieDialog()
        }
    }

    fun clickHideOpenBtn(isHidden: Boolean) {
        if (isHidden) openCookie() else hideCookie()
    }

    fun clickDeleteCookie() {
        showDeleteCookieDialog()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_RESUME) {
            klipContractTxRepository.getResult { result, tx_hash ->
                Timber.d("requestBuyACookie result($result, tx_hash=$tx_hash)")
                if (result && tx_hash != null && cookieDetail.value != null) {
                    viewModelScope.launch {
                        val purchaserUserId = userRepository.getLoginUser()?.userId ?: ""
                        if (cookieRepository.purchaseCookie(purchaserUserId, cookieDetail.value!!)) {
                            uiStateObserver.update(UiState.OnSuccess)
                            showPurchaseCookieSuccessDialog()
                        } else {
                            uiStateObserver.update(UiState.OnFail)
                        }
                    }
                } else {
                    Timber.d("requestBuyACookie result($result, tx_hash=$tx_hash)")
                    uiStateObserver.update(UiState.OnFail)
                }
            }
        }
    }
}
