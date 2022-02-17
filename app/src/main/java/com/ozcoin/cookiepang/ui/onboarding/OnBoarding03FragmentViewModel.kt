package com.ozcoin.cookiepang.ui.onboarding

import com.ozcoin.cookiepang.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoarding03FragmentViewModel @Inject constructor() : BaseViewModel() {

    private fun navigateToMain() {
        navigateTo(OnBoarding03FragmentDirections.actionMain())
    }

    fun clickSkip() {
        navigateToMain()
    }

    fun clickConfirm() {
        navigateToMain()
    }
}
