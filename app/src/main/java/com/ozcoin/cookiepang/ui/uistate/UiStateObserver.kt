package com.ozcoin.cookiepang.ui.uistate

import com.ozcoin.cookiepang.utils.UiState

class UiStateObserver(
    private val observer: (UiState) -> Unit
) {

    fun update(uiState: UiState) {
        observer(uiState)
    }
}
