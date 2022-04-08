package com.ozcoin.cookiepang.ui.cookiedetail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.contract.ContractRepository
import com.ozcoin.cookiepang.domain.cookie.CookieRepository
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetailRepository
import com.ozcoin.cookiepang.domain.klip.KlipContractTxRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.DataResult
import com.ozcoin.cookiepang.utils.DialogUtil
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.TitleClickListener
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.EventObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class CookieDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val cookieRepository: CookieRepository,
    private val contractRepository: ContractRepository,
    private val cookieDetailRepository: CookieDetailRepository,
    private val klipContractTxRepository: KlipContractTxRepository
) : BaseViewModel() {

    companion object {
        private const val KLIP_PENDING_TYPE_SALE_ON = 910
        private const val KLIP_PENDING_TYPE_BUY = 911
        private const val KLIP_PENDING_TYPE_REMOVE = 912
    }

    val titleClickListener = TitleClickListener(
        EventObserver {
            viewModelScope.launch { _eventFlow.emit(it) }
        }
    )

    private var klipPendingType = -1

    val state: StateFlow<CookieDetailUiState>
    val action: (CookieDetailUiAction) -> Unit

    init {
        val actionSharedFlow = MutableSharedFlow<CookieDetailUiAction>()

        val loadCookieDetail = actionSharedFlow
            .filterIsInstance<CookieDetailUiAction.LoadCookieDetail>()
            .distinctUntilChanged()
            .onStart { emit(CookieDetailUiAction.LoadCookieDetail("")) }

        val hideCookie = actionSharedFlow
            .filterIsInstance<CookieDetailUiAction.HideCookie>()
            .distinctUntilChanged()

        state = flatUiAction(loadCookieDetail, hideCookie)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = CookieDetailUiState()
            )

        action = {
            viewModelScope.launch { actionSharedFlow.emit(it) }
        }
    }

    private fun flatUiAction(
        loadCookieDetail: Flow<CookieDetailUiAction.LoadCookieDetail>,
        hideCookie: Flow<CookieDetailUiAction.HideCookie>
    ): Flow<CookieDetailUiState> {
        val loadCookieDetailFlow = loadCookieDetail
            .flatMapLatest { loadCookieDetail(it.cookieId) }

        val hideCookieFlow = hideCookie
            .flatMapConcat { hideCookie(it.cookieDetail) }

        val refreshCookieDetailFlow = hideCookieFlow
            .filter { it.loadState is UiState.OnSuccess && it.cookieDetail != null }
            .distinctUntilChanged()
            .flatMapConcat { loadCookieDetail(it.cookieDetail!!.cookieId.toString()) }

        return loadCookieDetailFlow
            .flatMapMerge { hideCookieFlow }
            .flatMapMerge { refreshCookieDetailFlow }
    }

    private fun loadCookieDetail(cookieId: String) = flow {
        emit(CookieDetailUiState(loadState = UiState.OnLoading))

        var loadState: UiState = UiState.OnFail
        val result = getCookieDetail(cookieId)
        var isHiddenByOtherUser = false
        val cookieDetail = if (result is DataResult.OnSuccess) {
            loadState = UiState.OnSuccess
            result.response
        } else {
            if (result is DataResult.OnFail && result.errorCode == 403) {
                isHiddenByOtherUser = true
            }
            null
        }

        emit(
            CookieDetailUiState(
                loadState = loadState,
                isHiddenCookieByOtherUser = isHiddenByOtherUser,
                cookieDetail = cookieDetail
            )
        )
    }

    private suspend fun getCookieDetail(cookieId: String): DataResult<CookieDetail>? {
        return if (cookieId.isNotEmpty()) {
            userRepository.getLoginUser()
                ?.let { cookieDetailRepository.getCookieDetail(it.userId, cookieId) }
        } else {
            null
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
                    showPurchaseCookieFailDialog()
                }
            } else {
                Timber.d("cookieDetail or loginUser is null")
                uiStateObserver.update(UiState.OnFail)
                showPurchaseCookieFailDialog()
            }
        }
    }

    private fun showPurchaseCookieSuccessDialog() {
        activityEventObserver.update(
            Event.ShowDialog(
                DialogUtil.getPurchaseCookieSuccessContents(),
                callback = {
                    if (it) refreshCookieDetail() else Timber.d("PurchaseCookieSuccessDialog cancelled")
                }
            )
        )
    }

    private fun showPurchaseCookieFailDialog() {
        activityEventObserver.update(
            Event.ShowDialog(
                DialogUtil.getPurchaseCookieFailContents(),
                callback = {
                    if (it) {
                        purchaseCookie()
                    }
                }
            )
        )
    }

    private fun showPurchaseCookieDialog() {
        activityEventObserver.update(
            Event.ShowDialog(
                DialogUtil.getConfirmPurchaseCookieContents(cookieDetail.value),
                callback = {
                    if (it) purchaseCookie() else Timber.d("ConfirmPurchaseCookieDialog cancelled")
                }
            )
        )
    }

    private fun showApproveWalletDialog() {
        activityEventObserver.update(
            Event.ShowDialog(
                DialogUtil.getWalletApproveRequiredContents(),
                callback = {
                    if (it) {
                        navigateTo(CookieDetailFragmentDirections.actionSetting())
                    }
                }
            )
        )
    }

    private fun showNotEnoughHammerDialog() {
        activityEventObserver.update(
            Event.ShowDialog(
                DialogUtil.getNotEnoughHammerContents(),
                callback = {
                    if (it) {
                        TODO("클레이튼 충전 진행해야함")
                    }
                }
            )
        )
    }

    private fun executePurchaseACookie() {
        viewModelScope.launch {
            val loginUser = userRepository.getLoginUser()
            val cookieDetail = cookieDetail.value
            if (loginUser != null && cookieDetail != null) {
                if (contractRepository.isWalletApproved(loginUser.userId)) {
                    if (contractRepository.getNumOfHammerBalance(loginUser.userId) >= BigInteger(
                            cookieDetail.hammerPrice.toString()
                        )
                    ) {
                        showPurchaseCookieDialog()
                    } else {
                        Timber.d("보유 해머 부족")
                        showNotEnoughHammerDialog()
                    }
                } else {
                    Timber.d("지갑 권한 미허용")
                    showApproveWalletDialog()
                }
            }
        }
    }

    private fun hideCookie(cookieDetail: CookieDetail?) = flow {
        if (cookieDetail != null) {
            emit(
                CookieDetailUiState(
                    loadState = UiState.OnLoading,
                    cookieDetail = cookieDetail
                )
            )

            val result = cookieDetailRepository.hideCookie(
                userRepository.getLoginUser()?.userId ?: "", cookieDetail
            )
            val loadState = if (result) {
                UiState.OnSuccess
            } else {
                UiState.OnFail
            }

            emit(
                CookieDetailUiState(
                    loadState = loadState,
                    cookieDetail = cookieDetail
                )
            )
        } else {
            emit(
                CookieDetailUiState(
                    loadState = UiState.OnFail,
                    cookieDetail = cookieDetail
                )
            )
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

    private fun openCookie() = flow {
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

    private fun refreshCookieDetail() =

        private

    fun showDeleteCookieDialog() {
        activityEventObserver.update(
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
            activityEventObserver.update(Event.Nav.ToEditCookie(it))
        }
    }

    fun clickCookieContentsBtn() {
        cookieDetail.value?.let {
            if (it.isMine) {
                navigateToEditCookie()
            } else {
                executePurchaseACookie()
            }
        }
    }

    fun clickHideOpenBtn(isHidden: Boolean) {
        state.value.cookieDetail?.let {
            if (isHidden) openCookie() else hideCookie(it)
        }
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
                            handleResultBuyACookie(tx_hash)
                        }
                        KLIP_PENDING_TYPE_REMOVE -> {
                            handleResultRemoveACookie(tx_hash)
                        }
                        KLIP_PENDING_TYPE_SALE_ON -> {
                            handleResultOnSaleACookie(tx_hash)
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

    private fun handleResultBuyACookie(tx_hash: String) {
        viewModelScope.launch {
            val purchaserUserId = userRepository.getLoginUser()?.userId ?: ""
            if (cookieRepository.purchaseCookie(purchaserUserId, tx_hash, cookieDetail.value!!)) {
                uiStateObserver.update(UiState.OnSuccess)
                showPurchaseCookieSuccessDialog()
            } else {
                uiStateObserver.update(UiState.OnFail)
            }
        }
    }

    private fun handleResultRemoveACookie(tx_hash: String) {
        viewModelScope.launch {
            if (cookieDetailRepository.removeCookie(tx_hash, cookieDetail.value!!)) {
                uiStateObserver.update(UiState.OnSuccess)
                navigateUp()
            } else {
                uiStateObserver.update(UiState.OnFail)
            }
        }
    }

    private fun handleResultOnSaleACookie(tx_hash: String) {
        requestOpenCookie()
    }
}
