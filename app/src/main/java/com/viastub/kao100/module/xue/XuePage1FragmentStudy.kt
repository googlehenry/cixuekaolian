package com.viastub.kao100.module.xue

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseFragment
import kotlinx.android.synthetic.main.activity_xue_detail_page_frag_study.*

class XuePage1FragmentStudy : BaseFragment(), GestureDetector.OnGestureListener {

    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_study
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpImageViewFlipper()
    }

    private var detector: GestureDetector? = null
    private fun setUpImageViewFlipper() {
//但是如果我希望HorizontalScrollView可以想ViewPager一样，既可以绑定数据集（动态改变图片），还能做到不管多少图片都不会OOM（ViewPager内部一直初始化，回收，最多保持3个View）。
        detector = GestureDetector(this)
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_02));
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_03));
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_04));
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_05));
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_06));
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_07));
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_08));
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_09));
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_10));
        flipper.addView(addImageView(R.drawable.demo_eng_pep_3_1_11));

    }

    private fun addImageView(id: Int): View? {
        val iv = ImageView(context)
        iv.id = id
        iv.setImageResource(id)
        return iv
    }

    override fun touchEventAware(): Boolean {
        return true
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
        if (flipper.currentView.id == R.drawable.demo_eng_pep_3_1_06) {
            toast("切换到当前页播放")
            val mediaPlayer = (mContext as XuePage1Activity).mediaPlayer
            mediaPlayer?.seekTo(88_000)
            mediaPlayer?.start()
        }
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