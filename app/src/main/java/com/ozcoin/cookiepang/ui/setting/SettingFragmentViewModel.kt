package com.ozcoin.cookiepang.ui.setting

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.common.URL_ANNOUNCEMENT_SERVICE
import com.ozcoin.cookiepang.common.URL_OFTEN_ASK_QUESTIONS
import com.ozcoin.cookiepang.common.URL_TERMS_OF_USE
import com.ozcoin.cookiepang.domain.contract.ContractRepository
import com.ozcoin.cookiepang.domain.klip.KlipContractTxRepository
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
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
class SettingFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val contractRepository: ContractRepository,
    private val klipContractTxRepository: KlipContractTxRepository
) : BaseViewModel(), LifecycleEventObserver {

    val titleClickListener = TitleClickListener(
        EventObserver {
            viewModelScope.launch { _eventFlow.emit(it) }
        }
    )

    private val _loginUser = MutableStateFlow<User?>(null)
    val loginUser: StateFlow<User?>
        get() = _loginUser

    lateinit var hideBtmMenu: () -> Unit
    lateinit var activityEventObserver: EventObserver
    lateinit var uiStateObserver: UiStateObserver

    fun loadUserInfo() {
        uiStateObserver.update(UiState.OnLoading)

        viewModelScope.launch {
            val loginUser = userRepository.getLoginUser()?.apply {
                numOfHammer = contractRepository.getNumOfHammerBalance(userId).toString()
                numOfKlaytn = contractRepository.getNumOfKlaytnBalance(userId).toString()
            }
            uiStateObserver.update(UiState.OnSuccess)
            _loginUser.emit(loginUser)
        }
    }

    private fun approveWallet() {
        loginUser.value?.let {
            uiStateObserver.update(UiState.OnLoading)
            viewModelScope.launch {
                if (contractRepository.isWalletApproved(it.userId)) {
                    uiStateObserver.update(UiState.OnSuccess)
                    showWalletApprovedSuccessDialog()
                } else {
                    if (!klipContractTxRepository.approveWallet(true))
                        uiStateObserver.update(UiState.OnFail)
                }
            }
        }
    }

    private fun showWalletApprovedSuccessDialog() {
        activityEventObserver.update(
            Event.ShowDialog(
                DialogUtil.getIsWalletApproveSuccessContents(), callback = { }
            )
        )
    }

    fun clickAllowWalletPermission() {
        approveWallet()
    }

    fun clickOftenAskQuestion() {
        viewModelScope.launch {
            _eventFlow.emit(Event.ShowWeb(URL_OFTEN_ASK_QUESTIONS))
        }
    }

    private fun navigateToEditProfile() {
        navigateTo(SettingFragmentDirections.actionEditProfile())
    }

    fun clickChangeProfile() {
        navigateToEditProfile()
    }

    fun clickAnnouncementService() {
        viewModelScope.launch {
            _eventFlow.emit(Event.ShowWeb(URL_ANNOUNCEMENT_SERVICE))
        }
    }

    fun clickTermsOfUse() {
        viewModelScope.launch {
            _eventFlow.emit(Event.ShowWeb(URL_TERMS_OF_USE))
        }
    }

    private fun releaseUser() {
        viewModelScope.launch {
            userRepository.logOut()
            hideBtmMenu()
            navigateTo(SettingFragmentDirections.actionSplash())
        }
    }

    private fun showLogOutDialog() {
        activityEventObserver.update(
            Event.ShowDialog(
                DialogUtil.getCheckKlipLogOutContents(),
                callback = {
                    if (it) releaseUser()
                }
            )
        )
    }

    fun clickRelease() {
        showLogOutDialog()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_RESUME) {
            klipContractTxRepository.getResult { result, tx_hash ->
                Timber.d("requestApproveWallet result($result, tx_hash=$tx_hash)")
                if (result && tx_hash != null) {
                    viewModelScope.launch {
                        uiStateObserver.update(UiState.OnSuccess)
                        showWalletApprovedSuccessDialog()
                    }
                } else {
                    Timber.d("requestApproveWallet result($result, tx_hash=$tx_hash)")
                    uiStateObserver.update(UiState.OnFail)
                }
            }
        }
    }
}
