package com.ozcoin.cookiepang.data.user

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
class UserUpdateRequestParam(
    val profilePicture: String,
    val backgroundPicture: String
)
