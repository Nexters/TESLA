package com.ozcoin.cookiepang.ui.cookiedetail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.common.TRANSITION_ANIM_DURATION
import com.ozcoin.cookiepang.domain.contract.ContractRepository
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
    private val contractRepository: ContractRepository,
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
                    delay(TRANSITION_ANIM_DURATION)
                    uiStateObserver.update(UiState.OnSuccess)
                    _cookieDetail.emit(result.response.apply { this.cookieId = cookieId.toInt() })
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

    private fun showApproveWalletDialog() {
        TODO("")
    }

    private fun notEnoughEnergy() {
        TODO("")
    }

    private fun executePurchaseACookie() {
        viewModelScope.launch {
            val loginUser = userRepository.getLoginUser()
            val cookieDetail = cookieDetail.value
            if (loginUser != null && cookieDetail != null) {
                if (contractRepository.isWalletApproved(loginUser.userId)) {
                    if (cookieDetail.hammerPrice <= contractRepository.getNumOfHammer(loginUser.userId)) {
                        showPurchaseCookieDialog()
                    } else {
                        Timber.d("보유 해머 부족")
                        notEnoughEnergy()
                    }
                } else {
                    Timber.d("지갑 권한 미허용")
                    showApproveWalletDialog()
                }
            }
        }
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
        val cookieDetail = cookieDetail.value
        if (cookieDetail != null) {
            viewModelScope.launch {
                if (contractRepository.isOnSaleCookie(cookieDetail.nftTokenId)) {
                    Timber.d("해당 쿠키 현재 판매 중 상태")
                    requestOpenCookie()
                } else {
                    Timber.d("해당 쿠키 현재 판매 중 상태 아님")
                    uiStateObserver.update(UiState.OnLoading)
                    viewModelScope.launch {
                        if (klipContractTxRepository.requestSaleOnACookie(cookieDetail.nftTokenId)) {
                            klipPendingType = KLIP_PENDING_TYPE_SALE_ON
                        } else {
                            uiStateObserver.update(UiState.OnFail)
                        }
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
        cookieDetail.value?.let {
            uiStateObserver.update(UiState.OnLoading)

            viewModelScope.launch {
                if (klipContractTxRepository.requestRemoveACookie(it.nftTokenId)) {
                    klipPendingType = KLIP_PENDING_TYPE_REMOVE
                } else {
                    uiStateObserver.update(UiState.OnFail)
                }
            }
        }
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

    private fun navigateToEditCookie() {
        cookieDetail.value?.toEditCookie()?.let {
            it.cookieId = cookieId.toInt()
            eventObserver.update(Event.Nav.ToEditCookie(it))
        }
    }

    fun clickCookieContentsBtn(isMine: Boolean) {
        if (isMine) {
            navigateToEditCookie()
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

    fun clickCollectorProfile() {
        cookieDetail.value?.collectorUserId?.let {
            navigateToUserProfile(it)
        }
    }

    fun clickCreatorProfile() {
        cookieDetail.value?.creatorUserId?.let {
            navigateToUserProfile(it)
        }
    }

    private fun navigateToUserProfile(userId: String) {
        navigateTo(CookieDetailFragmentDirections.actionMyHome(userId))
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
