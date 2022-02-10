package com.ozcoin.cookiepang.ui.splash

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.klip.KlipAuthRepository
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val klipAuthRepository: KlipAuthRepository
) : BaseViewModel() {

    val user = User()
    private var userAddress = ""

    fun isUserLogin() = klipAuthRepository.isUserLogin()

    fun setUserAddress(userAddress: String) {
        this.userAddress = userAddress
    }

    private fun regUser() {
        viewModelScope.launch {
            if (userRepository.regUser(user))
                klipAuthRepository.saveUserAddress(userAddress)
        }
    }

    fun finishActivity() {
        viewModelScope.launch {
            _eventFlow.emit(
                Event.FinishComponent.Activity
            )
        }
    }
}
