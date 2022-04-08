package com.ozcoin.cookiepang.ui.cookiedetail

import com.ozcoin.cookiepang.domain.cookiedetail.CookieDetail

sealed class CookieDetailUiAction {
    data class LoadCookieDetail(val cookieId: String) : CookieDetailUiAction()
    data class HideCookie(val cookieDetail: CookieDetail) : CookieDetailUiAction()
}
