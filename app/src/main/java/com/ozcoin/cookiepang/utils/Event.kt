package com.ozcoin.cookiepang.utils

import androidx.navigation.NavDirections
import com.ozcoin.cookiepang.domain.dialog.DialogContents

sealed class Event {

    sealed class FinishComponent : Event() {
        object Activity : FinishComponent()
    }

    sealed class StartComponent : Event() {
        class Activity(val target: Class<out Any>) : StartComponent()
    }

    sealed class FabAnim : Event() {
        object Left : FabAnim()
        object Right : FabAnim()
    }

    sealed class Nav : Event() {
        class To(val action: NavDirections) : Nav()
        object Up : Nav()
        object ToEditCookie : Nav()
    }

    class ShowDialog(
        val dialogContents: DialogContents,
        val callback: (Boolean) -> Unit
    ) : Event()
}
