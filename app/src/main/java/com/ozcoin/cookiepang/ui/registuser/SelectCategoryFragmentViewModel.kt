package com.ozcoin.cookiepang.ui.registuser

import com.ozcoin.cookiepang.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectCategoryFragmentViewModel @Inject constructor() : BaseViewModel() {

    var finishActivity: (() -> Unit)? = null

    private fun finishActivity() {
        finishActivity?.invoke()
    }

    private fun navigateToMain() {
        navigateTo(SelectCategoryFragmentDirections.actionMain())
    }

    fun clickNext() {
        navigateToMain()
        finishActivity()
    }
}
