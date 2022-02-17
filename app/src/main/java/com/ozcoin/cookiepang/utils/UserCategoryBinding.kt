package com.ozcoin.cookiepang.utils

import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.ozcoin.cookiepang.R
import com.ozcoin.cookiepang.domain.usercategory.UserCategoryColorStyle

object UserCategoryBinding {

    @JvmStatic
    @BindingAdapter("setUserCategoryColorStyle")
    fun setUserCategoryColorStyle(
        checkBox: CheckBox,
        userCategoryColorStyle: UserCategoryColorStyle?
    ) {
        if (userCategoryColorStyle != null) {
            setColorStyle(checkBox, userCategoryColorStyle)
        }
    }

    private fun setColorStyle(checkBox: CheckBox, userCategoryColorStyle: UserCategoryColorStyle) {
        val resId = when (userCategoryColorStyle) {
            UserCategoryColorStyle.NONE -> {
                R.drawable.selector_btn_category_stroke_none
            }
            UserCategoryColorStyle.BLUE -> {
                R.drawable.selector_btn_category_stroke_blue
            }
            UserCategoryColorStyle.ORANGE -> {
                R.drawable.selector_btn_category_stroke_orange
            }
            UserCategoryColorStyle.PINK -> {
                R.drawable.selector_btn_category_stroke_pink
            }
            UserCategoryColorStyle.PURPLE -> {
                R.drawable.selector_btn_category_stroke_purple
            }
        }

        val background = ContextCompat.getDrawable(checkBox.context, resId)
        checkBox.background = background
    }
}
