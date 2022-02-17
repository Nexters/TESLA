package com.ozcoin.cookiepang.ui

import com.ozcoin.cookiepang.domain.editcookie.EditCookie

sealed class MainEvent {
    sealed class FabAnim : MainEvent() {
        object Rotate0to405 : FabAnim()
        object Rotate405to0 : FabAnim()
    }

    data class NavigateToEditCookie(val editCookie: EditCookie? = null) : MainEvent()
}
