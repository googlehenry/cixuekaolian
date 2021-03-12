package com.qianli.cixuekaolian.wigets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet


class BrandTextView : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)

    override fun setTypeface(tf: Typeface?, style: Int) {
        super.setTypeface(tf, style)
//        if (style == Typeface.BOLD) {
//            super.setTypeface(
//                Typeface.createFromAsset(
//                    context.assets,
//                    "fonts/mononoki-Regular.ttf"
//                )
//            )
//        } else {
//            super.setTypeface(
//                Typeface.createFromAsset(
//                    context.assets,
//                    "fonts/mononoki-Regular.ttf"
//                )
//            )
//        }
    }
}