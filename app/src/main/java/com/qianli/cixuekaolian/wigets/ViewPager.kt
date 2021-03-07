package com.qianli.cixuekaolian.wigets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/*
Support to forbid viewpager to scroll tabs
 */
class ViewPager : ViewPager {
    var isScrollble = true

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (!isScrollble) {
            false
        } else super.onTouchEvent(ev)
    }
}