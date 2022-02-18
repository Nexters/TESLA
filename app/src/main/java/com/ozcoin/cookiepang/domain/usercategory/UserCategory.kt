package com.ozcoin.cookiepang.domain.usercategory

import android.os.Parcelable
import com.ozcoin.cookiepang.domain.selectcategory.SelectCategory
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserCategory(
    val categoryName: String,
    var isSelected: Boolean,
    val categoryColorStyle: UserCategoryColorStyle = UserCategoryColorStyle.NONE
) : Parcelable {
    companion object {
        fun typeAll(): UserCategory {
            return UserCategory("All", true, UserCategoryColorStyle.BLUE)
        }
    }
}

fun UserCategory.toSelectCategory(): SelectCategory {
    return SelectCategory(
        categoryName, isSelected
    )
}
