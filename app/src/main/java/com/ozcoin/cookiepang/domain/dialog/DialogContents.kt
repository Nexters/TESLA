package com.ozcoin.cookiepang.domain.dialog

data class DialogContents(
    val title: String,
    val contents: String,
    val confirm: String? = null,
    val cancel: String? = null
)
