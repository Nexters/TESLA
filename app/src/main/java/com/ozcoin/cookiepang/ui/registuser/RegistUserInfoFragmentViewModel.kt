package com.ozcoin.cookiepang.ui.registuser

import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistUserInfoFragmentViewModel @Inject constructor() : BaseViewModel() {

    private fun navigateToSelectCategory() {
        viewModelScope.launch {
//            _eventFlow.emit(
//                Event.Nav.To()
//            )
        }
    }

    fun clickRegistID() {
        navigateToSelectCategory()
    }
}
