package com.ozcoin.cookiepang.domain.usercategory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserCategory(
    val categoryName: String,
    var isSelected: Boolean,
    val categoryColorStyle: UserCategoryColorStyle = UserCategoryColorStyle.NONE,
    val categoryId: Int = -1
) : Parcelable {
    companion object {
        fun typeAll(): UserCategory {
            return UserCategory("전체", true, UserCategoryColorStyle.BLUE)
        }
    }
}

fun UserCategory.isTypeAll(): Boolean {
    return categoryName == "전체"
}
