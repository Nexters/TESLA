package com.ozcoin.cookiepang.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.updatePadding
import androidx.core.widget.TextViewCompat
import com.ozcoin.cookiepang.R
import kotlin.math.roundToInt

class LineHeightTextView : AppCompatTextView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {

        val attr = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.lineHeightTextAttrs,
            0, 0
        )
        try {
            val lineHeight = attr.getDimensionPixelSize(R.styleable.lineHeightTextAttrs_setLineHeight, -1)
            if (lineHeight != -1)
                TextViewCompat.setLineHeight(this, lineHeight)
        } finally {
            attr.recycle()
        }
    }

    private fun setCustomLineHeight(lineHeight: Int, factor: Float = 1.48f) {
        val lineSpacingExtra = lineHeight - factor * textSize

        val padding = lineSpacingExtra
            .takeIf { it != 0.0f }
            ?.div(2)
            ?.roundToInt()
            ?: 0

        setLineSpacing(lineSpacingExtra, 1f)
        updatePadding(
            top = padding,
            bottom = padding
        )
    }
}
