package com.ozcoin.cookiepang.domain.usercategory

data class UserCategory(
    val categoryName: String,
    var isSelected: Boolean
) {
    companion object {
        fun typeAll(): UserCategory {
            return UserCategory("All", true)
        }
    }
}
