package com.ozcoin.cookiepang.utils.observer

import com.ozcoin.cookiepang.ui.MainEvent

class MainEventObserver(
    private val observer: (MainEvent) -> Unit
) : Observer<MainEvent> {
    override fun update(update: MainEvent) {
        observer(update)
    }
}
