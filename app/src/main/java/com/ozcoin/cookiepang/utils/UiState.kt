package com.ozcoin.cookiepang.utils

sealed class UiState {
    object OnLoading : UiState()
    object OnSuccess : UiState()
    object OnFail : UiState()
}
