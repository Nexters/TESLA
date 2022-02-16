package com.ozcoin.cookiepang.utils.observer

import com.ozcoin.cookiepang.utils.UiState

class UiStateObserver(
    private val observer: (UiState) -> Unit
) : Observer<UiState> {

    override fun update(update: UiState) {
        observer(update)
    }
}
