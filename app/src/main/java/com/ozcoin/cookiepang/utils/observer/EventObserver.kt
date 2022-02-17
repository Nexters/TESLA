package com.ozcoin.cookiepang.utils.observer

import com.ozcoin.cookiepang.utils.Event

class EventObserver(
    private val observer: (Event) -> Unit
) : Observer<Event> {
    override fun update(update: Event) {
        observer(update)
    }
}
