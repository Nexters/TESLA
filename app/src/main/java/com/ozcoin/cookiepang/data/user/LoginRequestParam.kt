package com.ozcoin.cookiepang.data.user

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class LoginRequestParam(val walletAddress: String)
