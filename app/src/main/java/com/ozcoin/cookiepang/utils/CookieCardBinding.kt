package com.ozcoin.cookiepang.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
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

        if (view is ImageView) {
            Glide.with(view.context)
                .load(cookieCardResId)
                .into(view)
        } else {
            val drawable = ContextCompat.getDrawable(view.context, cookieCardResId)
            view.background = drawable
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["setCookieBoxImg", "setErrorImg"], requireAll = false)
    fun setCookieBoxImg(imageView: ImageView, cookieBoxImgUrl: String?, errorImgDrawable: Drawable?) {
        Glide.with(imageView.context)
            .load(cookieBoxImgUrl)
            .error(errorImgDrawable)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter(value = ["setCookieBoxCoverImg", "setErrorImg"], requireAll = false)
    fun setCookieBoxCoverImg(imageView: ImageView, cookieBoxCoverImgUrl: String?, errorImgDrawable: Drawable?) {
        Glide.with(imageView.context)
            .load(cookieBoxCoverImgUrl)
            .error(errorImgDrawable)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter(value = ["setCookieImg", "setErrorImg"], requireAll = false)
    fun setCookieImg(imageView: ImageView, cookieImgUrl: String?, errorImgDrawable: Drawable?) {
        Glide.with(imageView.context)
            .load(cookieImgUrl)
            .error(errorImgDrawable)
            .into(imageView)
    }
}
