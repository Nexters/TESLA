package com.ozcoin.cookiepang.ui.setting

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.common.URL_ANNOUNCEMENT_SERVICE
import com.ozcoin.cookiepang.common.URL_OFTEN_ASK_QUESTIONS
import com.ozcoin.cookiepang.common.URL_TERMS_OF_USE
import com.ozcoin.cookiepang.domain.klip.KlipAuthRepository
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val klipAuthRepository: KlipAuthRepository
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            loginUser = userRepository.getLoginUser()
        }
    }

    var loginUser: User? = null
        private set

    private fun approveWallet() {
        loginUser?.let {
            if (!it.isWalletApproved) {
                viewModelScope.launch {
                    klipAuthRepository.approveWallet(true)
                }
            }
        }
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
            klipAuthRepository.removeUserKlipAddress()
        }
    }

    fun clickRelease() {
        releaseUser()
    }
}
