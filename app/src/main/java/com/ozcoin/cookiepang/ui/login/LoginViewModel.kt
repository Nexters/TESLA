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

    val user = User()

    suspend fun isUserLogin(): Boolean {
        return (userRepository.getLoginUser() != null).also {
            Timber.d("isUserLogin($it)")
        }
    }

    fun setUserAddress(userAddress: String) {
        user.walletAddress = userAddress
    }
}
