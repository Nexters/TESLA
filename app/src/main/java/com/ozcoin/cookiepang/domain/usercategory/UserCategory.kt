package com.ozcoin.cookiepang.domain.usercategory

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserCategory(
    val categoryName: String,
    var isSelected: Boolean
) : Parcelable {
    companion object {
        fun typeAll(): UserCategory {
            return UserCategory("All", true)
        }
    }
}
