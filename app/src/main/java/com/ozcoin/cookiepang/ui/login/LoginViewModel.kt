package com.ozcoin.cookiepang.ui.login

import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.klip.KlipAuthRepository
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val klipAuthRepository: KlipAuthRepository
) : BaseViewModel() {

    val user = User()

    fun isUserLogin(): Flow<Boolean> {
        return klipAuthRepository.isUserLogin()
    }

    fun setUserAddress(userAddress: String) {
        user.walletAddress = userAddress
    }
}
