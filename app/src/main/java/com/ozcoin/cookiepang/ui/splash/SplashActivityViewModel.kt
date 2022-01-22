package com.ozcoin.cookiepang.ui.splash

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.repo.user.UserRegRepository
import com.ozcoin.cookiepang.utils.Event
import kotlinx.coroutines.launch

class SplashActivityViewModel(
    private val userRegRepository: UserRegRepository
): BaseViewModel() {

    fun isUserReg() = userRegRepository.isUserReg()

    fun finishActivity() {
        viewModelScope.launch {
            _eventFlow.emit(
                Event.FinishComponent.Activity
            )
        }
    }


}