package com.ozcoin.cookiepang.ui.cookiedetail

import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail
import com.ozcoin.cookiepang.utils.UiState

data class CookieDetailUiState(
    val loadState: UiState? = null,
    val isHiddenCookieByOtherUser: Boolean = false,
    val cookieDetail: CookieDetail? = null
)
