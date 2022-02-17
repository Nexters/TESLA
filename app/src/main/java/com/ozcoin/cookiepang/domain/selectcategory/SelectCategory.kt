package com.ozcoin.cookiepang.domain.selectcategory

import com.ozcoin.cookiepang.domain.usercategory.UserCategory

data class SelectCategory(
    val categoryName: String,
    var isSelected: Boolean
)

fun SelectCategory.toUserCategory(): UserCategory {
    return UserCategory(
        categoryName, isSelected
    )
}
