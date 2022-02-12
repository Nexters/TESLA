package com.ozcoin.cookiepang.ui.registuser

import com.ozcoin.cookiepang.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistUserInfoFragmentViewModel @Inject constructor() : BaseViewModel() {

    private fun navigateToSelectCategory() {
        navigateTo(RegistUserInfoFragmentDirections.actionSelectCategory())
    }

    fun clickRegistID() {
        navigateToSelectCategory()
    }
}
