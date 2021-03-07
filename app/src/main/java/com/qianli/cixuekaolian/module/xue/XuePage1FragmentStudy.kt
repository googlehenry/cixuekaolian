package com.qianli.cixuekaolian.module.xue

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.base.BaseFragment
import kotlinx.android.synthetic.main.activity_xue_page_frag_study.*

class XuePage1FragmentStudy : BaseFragment(), GestureDetector.OnGestureListener {

    override fun id(): Int {
        return R.layout.activity_xue_page_frag_study
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpImageViewFlipper()
    }

    private var detector: GestureDetector? = null
    private fun setUpImageViewFlipper() {

        detector = GestureDetector(this)
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_01));
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_02));
    }

    private fun addImageView(id: Int): View? {
        val iv = ImageView(context)
        iv.setImageResource(id)
        return iv
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return detector!!.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(
        e1: MotionEvent, e2: MotionEvent, velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (e1.x - e2.x > 120) {
            flipper.inAnimation = AnimationUtils.loadAnimation(context, R.anim.push_left_in)
            flipper.outAnimation = AnimationUtils.loadAnimation(context, R.anim.push_left_out)
            flipper.showNext()
            return true
        } else if (e1.x - e2.x < -120) {
            flipper.inAnimation = AnimationUtils.loadAnimation(context, R.anim.push_right_in)
            flipper.outAnimation = AnimationUtils.loadAnimation(context, R.anim.push_right_out)
            flipper.showPrevious()
            return true
        }
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onScroll(
        e1: MotionEvent?, e2: MotionEvent?, distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }
}