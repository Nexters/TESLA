package com.ozcoin.cookiepang.ui.onboarding

import com.ozcoin.cookiepang.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoarding02FragmentViewModel @Inject constructor() : BaseViewModel() {

    fun navigateToCompUserReg() {
        navigateTo(OnBoarding02FragmentDirections.actionCompUserReg())
    }

    private fun navigateToOnBoarding03() {
        navigateTo(OnBoarding02FragmentDirections.actionOnBoarding03())
    }

    fun clickNext() {
        navigateToOnBoarding03()
    }
}
