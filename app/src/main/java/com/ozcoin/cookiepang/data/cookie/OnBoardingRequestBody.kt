package com.ozcoin.cookiepang.data.cookie

import androidx.annotation.Keep
import com.ozcoin.cookiepang.domain.onboarding.OnBoardingCookie
import kotlinx.serialization.Serializable

@Keep
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
