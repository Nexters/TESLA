package com.ozcoin.cookiepang.ui.login

import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.klip.KlipAuthRepository
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRepository
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userCategoryRepository: UserCategoryRepository,
    private val klipAuthRepository: KlipAuthRepository
) : BaseViewModel() {

    val user = User()

    suspend fun isUserLogin(): Boolean {
        return userRepository.getLoginUser() != null
    }

    fun setUserAddress(userAddress: String) {
        user.walletAddress = userAddress
    }
}
