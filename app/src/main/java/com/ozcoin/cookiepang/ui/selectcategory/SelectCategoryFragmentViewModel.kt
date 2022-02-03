package com.ozcoin.cookiepang.ui.selectcategory

import com.ozcoin.cookiepang.base.BaseViewModel

class SelectCategoryFragmentViewModel : BaseViewModel() {

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
