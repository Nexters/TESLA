package com.ozcoin.cookiepang.domain.usercategory

import com.ozcoin.cookiepang.domain.feed.CookieCardStyle

enum class UserCategoryColorStyle {
    NONE, BLUE, ORANGE, PINK, PURPLE, LIME
}

fun UserCategoryColorStyle.toCookieCardStyle(): CookieCardStyle {
    return when (this) {
        UserCategoryColorStyle.BLUE -> CookieCardStyle.BLUE
        UserCategoryColorStyle.ORANGE -> CookieCardStyle.YELLOW
        UserCategoryColorStyle.PINK -> CookieCardStyle.PINK
        UserCategoryColorStyle.PURPLE -> CookieCardStyle.PURPLE
        UserCategoryColorStyle.LIME -> CookieCardStyle.YELLOW
        UserCategoryColorStyle.NONE -> CookieCardStyle.BLUE
    }
}
