package com.ozcoin.cookiepang.ui.registuser

import com.ozcoin.cookiepang.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectCategoryFragmentViewModel @Inject constructor() : BaseViewModel() {

    private fun navigateToCompleteUserReg() {
        navigateTo(SelectCategoryFragmentDirections.actionCompleteUserReg())
    }

    fun clickNext() {
        navigateToCompleteUserReg()
    }
}
