package com.ozcoin.cookiepang.domain.dialog

import android.text.SpannableString
import android.text.style.UnderlineSpan

data class DialogContents(
    val title: String,
    val contents: String,
    val confirm: String? = null,
    val cancel: String? = null,
    val dialogLink: DialogLink? = null
)

data class DialogLink(
    private val _contents: String,
    val link: String
) {
    val contents = SpannableString(_contents).apply {
        setSpan(UnderlineSpan(), 0, _contents.length, 0)
    }
}
