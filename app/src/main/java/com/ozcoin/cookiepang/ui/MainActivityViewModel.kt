package com.ozcoin.cookiepang.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.base.BaseViewModel
import com.ozcoin.cookiepang.utils.Event
import com.ozcoin.cookiepang.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _isBtmNavViewVisible = MutableStateFlow(true)
    val isBtmNavViewVisible: StateFlow<Boolean>
        get() = _isBtmNavViewVisible

    private val _isEditingCookie = MutableStateFlow(false)
    val isEditingCookie: StateFlow<Boolean>
        get() = _isEditingCookie

    private var currentFragmentId = -1
        set(value) {
            if (field != R.id.editCookie_dest && value == R.id.editCookie_dest) {
                isEditingCookie(true)
                animFab(Event.FabAnim.Left)
            } else if (field == R.id.editCookie_dest && value != R.id.editCookie_dest) {
                isEditingCookie(false)
                animFab(Event.FabAnim.Right)
            }
            field = value
        }

    private fun animFab(event: Event.FabAnim) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun isEditingCookie(isEditingCookie: Boolean) {
        viewModelScope.launch {
            _isEditingCookie.emit(isEditingCookie)
        }
    }

    fun setCurrentFragmentName(destinationId: Int) {
        currentFragmentId = destinationId
    }

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

    private fun navigateToEditCookie() {
        viewModelScope.launch {
            _eventFlow.emit(Event.Nav.ToEditCookie)
        }
    }

    private fun showCancelToEditingCookieDialog() {
        viewModelScope.launch {
        }
    }

    fun clickFabBtn() {
        if (!isEditingCookie.value)
            navigateToEditCookie()
        else
            showCancelToEditingCookieDialog()
    }

    fun clickInterceptBtnMenu() {
        Timber.d("Btm menu is intercepted haha")
        showCancelToEditingCookieDialog()
    }
}
