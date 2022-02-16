package com.ozcoin.cookiepang.ui

sealed class MainEvent {
    sealed class FabAnim : MainEvent() {
        object Rotate0to405 : FabAnim()
        object Rotate405to0 : FabAnim()
    }

    object NavigateToEditCookie : MainEvent()
}
