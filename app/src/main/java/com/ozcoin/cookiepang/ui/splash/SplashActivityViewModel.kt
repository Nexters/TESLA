package com.ozcoin.cookiepang.ui.splash

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.user.UserRegRepository
import com.ozcoin.cookiepang.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    private val userRegRepository: UserRegRepository
) : BaseViewModel() {

    fun isUserReg() = userRegRepository.isUserReg()

    fun finishActivity() {
        viewModelScope.launch {
            _eventFlow.emit(
                Event.FinishComponent.Activity
            )
        }
    }
}
