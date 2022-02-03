package com.ozcoin.cookiepang.ui.login

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.data.user.User
import com.ozcoin.cookiepang.repo.user.UserRegRepository
import kotlinx.coroutines.launch

class LoginFragmentViewModel(
    private val userRegRepository: UserRegRepository
) : BaseViewModel() {

    private suspend fun regUser() = userRegRepository.regUser(User("오지코인"))

    private fun navigateToSelectCategory() {
        navigateTo(LoginFragmentDirections.actionSelectCategory())
    }

    fun clickLogin() {
        viewModelScope.launch {
            if (regUser()) {
                navigateToSelectCategory()
            }
        }
    }
}
