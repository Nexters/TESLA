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
            return UserCategory("모두 보기", true, UserCategoryColorStyle.BLUE, categoryId = Int.MIN_VALUE)
        }
    }
}

fun UserCategory.isTypeAll(): Boolean {
    return categoryName == "모두 보기"
}
