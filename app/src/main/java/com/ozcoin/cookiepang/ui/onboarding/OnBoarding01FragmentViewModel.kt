package com.ozcoin.cookiepang.ui.onboarding

import com.ozcoin.cookiepang.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoarding01FragmentViewModel @Inject constructor() : BaseViewModel() {

    fun navigateToCompUserReg() {
        navigateTo(OnBoarding02FragmentDirections.actionCompUserReg())
    }

    private fun navigateToOnBoarding02() {
        navigateTo(OnBoarding01FragmentDirections.actionOnBoarding02())
    }

    fun clickNext() {
        navigateToOnBoarding02()
    }
}
