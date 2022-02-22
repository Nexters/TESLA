package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
enum class CookieQueryTargetType {
    COLLECTED, COOKIES
}
