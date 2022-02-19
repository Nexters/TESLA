package com.ozcoin.cookiepang.utils

import androidx.navigation.NavDirections
import com.ozcoin.cookiepang.domain.dialog.DialogContents
import com.ozcoin.cookiepang.domain.editcookie.EditCookie

sealed class Event {

    sealed class Nav : Event() {
        class To(val action: NavDirections) : Nav()
        data class Up(val key: String? = null, val value: Any? = null) : Nav()
        data class ToEditCookie(val editCookie: EditCookie? = null) : Nav()
        object ToAlarm : Nav()
        object ToSetting : Nav()
    }

    sealed class FabAnim : Event() {
        object Rotate0to405 : FabAnim()
        object Rotate405to0 : FabAnim()
    }

    data class ShowDialog(
        val dialogContents: DialogContents,
        val callback: (Boolean) -> Unit
    ) : Event()

    data class ShowWeb(val url: String) : Event()
}
