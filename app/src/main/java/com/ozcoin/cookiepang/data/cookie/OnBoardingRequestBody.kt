package com.ozcoin.cookiepang.data.cookie

import com.ozcoin.cookiepang.ui.onboarding.OnBoardingCookie
import kotlinx.serialization.Serializable

@Serializable
data class OnBoardingRequestBody(
    val question: String,
    val answer: String
)

fun OnBoardingCookie.toData(): OnBoardingRequestBody {
    return OnBoardingRequestBody(
        question, answer
    )
}
