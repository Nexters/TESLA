package com.ozcoin.cookiepang.ui.login

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.klip.KlipAuthRepository
import com.ozcoin.cookiepang.domain.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val klipAuthRepository: KlipAuthRepository
) : BaseViewModel(), LifecycleEventObserver {

    var regUserAddress: ((String) -> Unit)? = null

    private fun navigateToRegistID() {
        navigateTo(LoginFragmentDirections.actionRegistID())
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
                    val address = address.lowercase(Locale.getDefault())
                    regUserAddress?.invoke(address)
                    viewModelScope.launch {
                        if (userRepository.isUserRegistration(address)) {
                            navigateTo(LoginFragmentDirections.actionMain())
                        } else {
                            navigateToRegistID()
                        }
                    }
                }
            }
        }
    }
}
