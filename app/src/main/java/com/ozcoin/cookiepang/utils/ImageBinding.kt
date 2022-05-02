package com.ozcoin.cookiepang.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.domain.alarm.AlarmType

object ImageBinding {

    @JvmStatic
    @BindingAdapter(value = ["loadCircleImg", "setErrorImg"], requireAll = true)
    fun loadCircleImg(imageView: ImageView, drawable: Drawable?, errorImgDrawable: Drawable?) {
        if (errorImgDrawable != null)
            setCircleImg(imageView, drawable, errorImgDrawable)
    }

    @JvmStatic
    @BindingAdapter(value = ["loadCircleImg", "setErrorImg"], requireAll = true)
    fun loadCircleImg(imageView: ImageView, url: String?, errorImgDrawable: Drawable?) {
        if (errorImgDrawable != null)
            setCircleImg(imageView, url, errorImgDrawable)
    }

    private fun setCircleImg(
        imageView: ImageView,
        drawable: Drawable?,
        errorImgDrawable: Drawable
    ) {
        Glide.with(imageView.context)
            .load(drawable)
            .apply(RequestOptions().circleCrop().centerCrop())
            .error(errorImgDrawable)
            .into(imageView)
    }

    private fun setCircleImg(imageView: ImageView, url: String?, errorImgDrawable: Drawable) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions().circleCrop().centerCrop())
            .error(errorImgDrawable)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter(value = ["loadProfileBackgroundImg", "setErrorImg"], requireAll = true)
    fun loadProfileBackgroundImg(imageView: ImageView, url: String?, errorImgDrawable: Drawable?) {
        if (errorImgDrawable != null)
            Glide.with(imageView.context)
                .load(url)
                .centerCrop()
                .error(errorImgDrawable)
                .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("loadAlarmIconByType")
    fun loadAlarmIconByType(imageView: ImageView, alarmType: AlarmType?) {
        if (alarmType != null) {
            val imgRes = when (alarmType) {
                AlarmType.ASK -> R.drawable.ic_ask_alarm
                AlarmType.SALE -> R.drawable.ic_sale_alarm
            }
            Glide.with(imageView.context)
                .load(imgRes)
                .into(imageView)
        }
    }
}
