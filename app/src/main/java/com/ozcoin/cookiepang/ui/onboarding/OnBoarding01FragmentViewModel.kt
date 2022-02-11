package com.ozcoin.cookiepang.ui.onboarding

import com.ozcoin.cookiepang.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoarding01FragmentViewModel @Inject constructor() : BaseViewModel() {

    fun navigateToMain() {
        navigateTo(OnBoarding02FragmentDirections.actionMain())
    }

    private fun navigateToOnBoarding02() {
        navigateTo(OnBoarding01FragmentDirections.actionOnBoarding02())
    }

    fun clickNext() {
        navigateToOnBoarding02()
    }
}
