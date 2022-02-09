package com.ozcoin.cookiepang.utils

import androidx.navigation.NavDirections

sealed class Event {

    sealed class FinishComponent : Event() {
        object Activity : FinishComponent()
    }

    sealed class StartComponent : Event() {
        class Activity(val target: Class<out Any>) : StartComponent()
    }

    sealed class Nav : Event() {
        class To(val action: NavDirections) : Nav()
        object Up : Nav()
    }
}
