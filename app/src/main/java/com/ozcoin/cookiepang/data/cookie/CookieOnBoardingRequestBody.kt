package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CookieOnBoardingRequestBody(
    val creatorId: Int,
    val defaultCookies: List<OnBoardingRequestBody>
)
