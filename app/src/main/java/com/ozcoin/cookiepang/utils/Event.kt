package com.ozcoin.cookiepang.utils

sealed class Event {

    sealed class FinishComponent : Event() {
        object Activity : FinishComponent()
    }

    sealed class StartComponent : Event() {
        class Activity(val target: Class<out Any>) : StartComponent()
    }

}