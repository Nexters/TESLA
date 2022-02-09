package com.ozcoin.cookiepang.ui.login

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.user.User
import com.ozcoin.cookiepang.domain.user.UserRegRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val userRegRepository: UserRegRepository
) : BaseViewModel() {

    private suspend fun regUser() = userRegRepository.regUser(User("오지코인"))

    private fun navigateToSelectCategory() {
        navigateTo(LoginFragmentDirections.actionRegistID())
    }

    fun clickLogin() {
        viewModelScope.launch {
            if (regUser()) {
                navigateToSelectCategory()
            }
        }
    }
}
