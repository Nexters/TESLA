package com.ozcoin.cookiepang.utils

import com.ozcoin.cookiepang.utils.observer.EventObserver

class TitleClickListener(
    private val eventObserver: EventObserver
) {

    fun clickShare() {
    }

    fun clickAlarm() {
        eventObserver.update(Event.Nav.ToAlarm)
    }

    fun clickSetting() {
        eventObserver.update(Event.Nav.ToSetting)
    }

    fun clickBack() {
        eventObserver.update(Event.Nav.Up())
    }

    fun clickClose() {
        eventObserver.update(Event.Nav.Up())
    }
}
