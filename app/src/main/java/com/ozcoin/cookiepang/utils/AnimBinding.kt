package com.ozcoin.cookiepang.utils

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ozcoin.cookiepang.R

object AnimBinding {

    @JvmStatic
    @BindingAdapter("bind:inputReachingTheLimit")
    fun inputReachingTheLimit(textView: TextView, isReachingTheLimit: Boolean?) {
        isReachingTheLimit?.let {
            if (it) startBounceAnim(textView)
        }
    }

    private fun startBounceAnim(view: View) {
        view.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.bounce))
    }
}
