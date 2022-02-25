package com.ozcoin.cookiepang.ui.login

import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    var user = User()
        private set

    suspend fun isUserLogin(): Boolean {
        val loginUser = userRepository.getLoginUser()
        return (loginUser != null).also {
            kotlin.runCatching { user = loginUser!! }
            Timber.d("isUserLogin($it)")
        }
    }

    suspend fun isFinishOnBoarding(): Boolean {
        return userRepository.getLoginUser()?.finishOnboard ?: false
    }

    fun setUserAddress(userAddress: String) {
        user.walletAddress = userAddress
    }
}
