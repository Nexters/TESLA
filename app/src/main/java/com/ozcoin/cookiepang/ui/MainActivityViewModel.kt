package com.ozcoin.cookiepang.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _isBtmNavViewVisible = MutableStateFlow(true)
    val isBtmNavViewVisible: StateFlow<Boolean>
        get() = _isBtmNavViewVisible

    fun showBtmNavView() {
        viewModelScope.launch { _isBtmNavViewVisible.emit(true) }
    }

    fun hideBtmNavView() {
        viewModelScope.launch { _isBtmNavViewVisible.emit(false) }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun updateUiState(uiState: UiState) {
        viewModelScope.launch {
            _uiStateFlow.emit(uiState)
        }
    }
}
