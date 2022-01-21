package com.ozcoin.cookiepang.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ozcoin.cookiepang.base.BaseViewModel

class MainVm : BaseViewModel() {

    private val _useTitleBar = MutableLiveData<Boolean>().also { it.value = false }
    val useTitleBar : LiveData<Boolean>
        get() = _useTitleBar

    private val _useBottomNav = MutableLiveData<Boolean>().also { it.value = false }
    val useBottomNav : LiveData<Boolean>
        get() = _useBottomNav

    fun setUseTitleBar(useTitleBar: Boolean) {
        _useTitleBar.postValue(useTitleBar)
    }

    fun setUseBottomNav(useBottomNav: Boolean) {
        _useBottomNav.postValue(useBottomNav)
    }

}