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
import kotlinx.coroutines.delay
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

    companion object {
        private const val KLIP_PENDING_TYPE_SALE_ON = 910
        private const val KLIP_PENDING_TYPE_BUY = 911
        private const val KLIP_PENDING_TYPE_REMOVE = 912
    }

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
    private var klipPendingType = -1

    fun getCookieDetail(cookieId: String) {
        if (cookieId.isNotEmpty()) {
            this.cookieId = cookieId
            uiStateObserver.update(UiState.OnLoading)

            viewModelScope.launch {
                val result = userRepository.getLoginUser()
                    ?.let { cookieDetailRepository.getCookieDetail(it.userId, cookieId) }
                if (result is DataResult.OnSuccess) {
                    Timber.d("getCookieDetail($cookieId) is success")
                    delay(400L)
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
                if (klipContractTxRepository.requestBuyACookie(cookieDetail.value!!)) {
                    klipPendingType = KLIP_PENDING_TYPE_BUY
                } else {
                    uiStateObserver.update(UiState.OnFail)
                }
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
                    if (it) refreshCookieDetail() else Timber.d("PurchaseCookieSuccessDialog cancelled")
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
        cookieDetail.value?.let {
            uiStateObserver.update(UiState.OnLoading)

            viewModelScope.launch {
                val result = cookieDetailRepository.hideCookie(
                    userRepository.getLoginUser()?.userId ?: "", it
                )
                if (result) {
                    uiStateObserver.update(UiState.OnSuccess)
                    refreshCookieDetail()
                } else {
                    uiStateObserver.update(UiState.OnFail)
                }
            }
        }
    }

    private fun openCookie() {
        cookieDetail.value?.let {
            if (it.isOnSale) {
                requestOpenCookie()
            } else {
                uiStateObserver.update(UiState.OnLoading)
                viewModelScope.launch {
                    if (klipContractTxRepository.requestSaleOnACookie(it)) {
                        klipPendingType = KLIP_PENDING_TYPE_SALE_ON
                    } else {
                        uiStateObserver.update(UiState.OnFail)
                    }
                }
            }
        }
    }

    private fun requestOpenCookie() {
        cookieDetail.value?.let {
            uiStateObserver.update(UiState.OnLoading)

            viewModelScope.launch {
                val result = cookieDetailRepository.openCookie(
                    userRepository.getLoginUser()?.userId ?: "", it
                )
                if (result) {
                    uiStateObserver.update(UiState.OnSuccess)
                    refreshCookieDetail()
                } else {
                    uiStateObserver.update(UiState.OnFail)
                }
            }
        }
    }

    private fun deleteCookie() {
        TODO("deleteCookie")
    }

    private fun refreshCookieDetail() {
        getCookieDetail(cookieId)
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
                Timber.d("requestResult($result, tx_hash=$tx_hash)")
                if (result && tx_hash != null && cookieDetail.value != null) {
                    when (klipPendingType) {
                        KLIP_PENDING_TYPE_BUY -> {
                            handleResultBuyACookie()
                        }
                        KLIP_PENDING_TYPE_REMOVE -> {
                            handleResultRemoveACookie()
                        }
                        KLIP_PENDING_TYPE_SALE_ON -> {
                            handleResultOnSaleACookie()
                        }
                    }
                    klipPendingType = -1
                } else {
                    Timber.d("requestBuyACookie result($result, tx_hash=$tx_hash)")
                    uiStateObserver.update(UiState.OnFail)
                }
            }
        }
    }

    private fun handleResultBuyACookie() {
        viewModelScope.launch {
            val purchaserUserId = userRepository.getLoginUser()?.userId ?: ""
            if (cookieRepository.purchaseCookie(purchaserUserId, cookieDetail.value!!)) {
                uiStateObserver.update(UiState.OnSuccess)
                showPurchaseCookieSuccessDialog()
            } else {
                uiStateObserver.update(UiState.OnFail)
            }
        }
    }

    private fun handleResultRemoveACookie() {
        viewModelScope.launch {
            if (cookieDetailRepository.removeCookie(cookieDetail.value!!)) {
                uiStateObserver.update(UiState.OnSuccess)
                navigateUp()
            } else {
                uiStateObserver.update(UiState.OnFail)
            }
        }
    }

    private fun handleResultOnSaleACookie() {
        requestOpenCookie()
    }
}
