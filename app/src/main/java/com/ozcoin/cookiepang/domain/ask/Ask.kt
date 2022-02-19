package com.ozcoin.cookiepang.domain.ask

import com.ozcoin.cookiepang.domain.usercategory.UserCategory

data class Ask(
    val userId: String,
    var question: String,
    var selectedCategory: UserCategory? = null
)
