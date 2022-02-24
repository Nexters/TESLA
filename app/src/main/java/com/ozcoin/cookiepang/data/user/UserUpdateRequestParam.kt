package com.ozcoin.cookiepang.data.user

import kotlinx.serialization.Serializable

@Serializable
class UserUpdateRequestParam(
    val profilePicture: String,
    val backgroundPicture: String
)
