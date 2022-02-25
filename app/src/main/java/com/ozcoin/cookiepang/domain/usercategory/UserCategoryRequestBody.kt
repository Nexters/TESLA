package com.ozcoin.cookiepang.domain.usercategory

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class UserCategoryRequestBody(
    val categoryIdList: List<Int>
)
