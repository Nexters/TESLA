package com.ozcoin.cookiepang.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ozcoin.cookiepang.domain.cookiehistory.CookieHistoryType

object TextBinding {

    @JvmStatic
    @BindingAdapter("bind:setCookieHistoryType")
    fun setCookieHistoryType(textView: TextView, cookieHistoryType: CookieHistoryType?) {
        if (cookieHistoryType != null) {
            val string = when (cookieHistoryType) {
                CookieHistoryType.CREATE -> "생성"
                CookieHistoryType.MODIFY -> "구매"
                CookieHistoryType.PURCHASE -> "수정"
            }
            textView.text = string
        }
    }
}
