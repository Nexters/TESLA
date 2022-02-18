package com.ozcoin.cookiepang.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageBinding {

    @JvmStatic
    @BindingAdapter(value = ["loadCircleImg", "setErrorImg"], requireAll = false)
    fun loadCircleImg(imageView: ImageView, drawable: Drawable?, errorImgDrawable: Drawable?) {
        setCircleImg(imageView, drawable, errorImgDrawable)
    }

    @JvmStatic
    @BindingAdapter(value = ["loadCircleImg", "setErrorImg"], requireAll = false)
    fun loadCircleImg(imageView: ImageView, url: String?, errorImgDrawable: Drawable?) {
        setCircleImg(imageView, url, errorImgDrawable)
    }

    private fun setCircleImg(imageView: ImageView, drawable: Drawable?, errorImgDrawable: Drawable?) {
        Glide.with(imageView.context)
            .load(drawable)
            .apply(RequestOptions().circleCrop().centerCrop())
            .error(errorImgDrawable)
            .into(imageView)
    }

    private fun setCircleImg(imageView: ImageView, url: String?, errorImgDrawable: Drawable?) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions().circleCrop().centerCrop())
            .error(errorImgDrawable)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter(value = ["loadProfileBackgroundImg", "setErrorImg"], requireAll = false)
    fun loadProfileBackgroundImg(imageView: ImageView, url: String?, errorImgDrawable: Drawable?) {
        Glide.with(imageView.context)
            .load(url)
            .error(errorImgDrawable)
            .into(imageView)
    }
}
