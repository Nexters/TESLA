package com.ozcoin.cookiepang.domain.usercategory

import kotlinx.serialization.Serializable

@Serializable
data class UserCategoryRequestBody(
    val categoryIdList: List<Int>
)
