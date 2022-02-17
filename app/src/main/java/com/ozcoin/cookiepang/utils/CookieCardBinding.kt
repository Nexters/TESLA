package com.ozcoin.cookiepang.utils

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.domain.feed.CookieCardStyle

object CookieCardBinding {

    @JvmStatic
    @BindingAdapter(value = ["setCookieCardStyle", "isHidden"], requireAll = true)
    fun setCookieCard(view: View, cookieCardStyle: CookieCardStyle?, isHidden: Boolean?) {
        if (cookieCardStyle != null && isHidden != null) {
            if (isHidden) {
                setHiddenCookieCard(view, cookieCardStyle)
            } else {
                setOpenedCookieCard(view, cookieCardStyle)
            }
        }
    }

    private fun setOpenedCookieCard(view: View, cookieCardStyle: CookieCardStyle) {
        val cookieCardResId = when (cookieCardStyle) {
            CookieCardStyle.BLUE -> R.drawable.bg_feed_contents_answer_blue
            CookieCardStyle.PINK -> R.drawable.bg_feed_contents_answer_pink
            CookieCardStyle.YELLOW -> R.drawable.bg_feed_contents_answer_yellow
        }

        val drawable = ContextCompat.getDrawable(view.context, cookieCardResId)
        view.background = drawable
    }

    private fun setHiddenCookieCard(view: View, cookieCardStyle: CookieCardStyle) {
        val cookieCardResId = when (cookieCardStyle) {
            CookieCardStyle.BLUE -> R.drawable.ic_feed_hidden_cookie_blue
            CookieCardStyle.PINK -> R.drawable.ic_feed_hidden_cookie_pink
            CookieCardStyle.YELLOW -> R.drawable.ic_feed_hidden_cookie_yellow
        }

        val drawable = ContextCompat.getDrawable(view.context, cookieCardResId)
        view.background = drawable
    }
}
