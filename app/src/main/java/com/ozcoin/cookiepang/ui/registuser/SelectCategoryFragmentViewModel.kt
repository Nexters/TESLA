package com.ozcoin.cookiepang.ui.registuser

import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.domain.selectcategory.SelectCategory
import com.ozcoin.cookiepang.domain.usercategory.UserCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectCategoryFragmentViewModel @Inject constructor() : BaseViewModel() {

    lateinit var getSelectedCategory: () -> List<UserCategory>

    private fun checkSelectedCategoryListCnt(): Boolean {
        return getSelectedCategory().size >= 3
    }

    private fun navigateToCompleteUserReg() {
        navigateTo(SelectCategoryFragmentDirections.actionCompleteUserReg())
    }

    suspend fun getCategoryList(): List<SelectCategory> {
        return emptyList()
    }

    fun clickNext() {
        if (checkSelectedCategoryListCnt())
            navigateToCompleteUserReg()
    }
}
