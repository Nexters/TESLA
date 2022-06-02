package com.ozcoin.cookiepang.ui.login

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.klip.KlipAuthRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.utils.UiState
import com.ozcoin.cookiepang.utils.observer.UiStateObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val klipAuthRepository: KlipAuthRepository
) : BaseViewModel(), LifecycleEventObserver {

    var regUserAddress: ((String) -> Unit)? = null
    lateinit var uiStateObserver: UiStateObserver

    private fun navigateToRegistID() {
        navigateTo(LoginFragmentDirections.actionRegistID())
    }

    fun navigateToSelectCategory() {
        navigateTo(LoginFragmentDirections.actionSelectCategory())
    }

    fun clickLogin() {
        viewModelScope.launch {
            klipAuthRepository.requestAuth(null)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_RESUME) {
            klipAuthRepository.getAuthResult { result, address ->
                if (result && address != null) {
                    uiStateObserver.update(UiState.OnLoading)
                    val address = address.lowercase(Locale.getDefault())
                    regUserAddress?.invoke(address)

                    viewModelScope.launch {
                        if (userRepository.isUserRegistration(address)) {
                            val loginUser = userRepository.getLoginUser()
                            uiStateObserver.update(UiState.OnSuccess)
                            if (loginUser != null) {
                                if (loginUser.finishOnboard) {
                                    navigateTo(LoginFragmentDirections.actionMain())
                                } else {
                                    navigateToSelectCategory()
                                }
                            } else {
                                Timber.d("login user is null")
                            }
                        } else {
                            uiStateObserver.update(UiState.OnSuccess)
                            navigateToRegistID()
                        }
                    }
                }
            }
        }
    }
}
