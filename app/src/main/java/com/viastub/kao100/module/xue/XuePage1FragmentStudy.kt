package com.viastub.kao100.module.xue

import android.net.Uri
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.utils.VariablesXue
import kotlinx.android.synthetic.main.activity_xue_detail_page_frag_study.*
import java.io.File


class XuePage1FragmentStudy(var pageSnapshotPaths: MutableList<String>?) : BaseFragment(),
    GestureDetector.OnGestureListener {

    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_study
    }

    var currentIndex = -1


    private var detector: GestureDetector? = null
    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        detector = GestureDetector(this)

        pageSnapshotPaths?.let {
            currentIndex = 0
            teaching_book_unit_page_index.text = (currentIndex + 1).toString()
            VariablesXue.xueContext?.currentPageIndex = currentIndex

            teaching_book_unit_progress.max = it.size
            teaching_book_unit_progress.secondaryProgress = currentIndex + 1
            flipper.addView(getImageView(File(it[currentIndex])));
        }

    }


    private fun getImageView(imageFile: File): View {
        val iv = ImageView(context)
        iv.setImageURI(Uri.fromFile(imageFile))
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
            pageSnapshotPaths?.let {
                currentIndex++
                if (currentIndex < it.size) {
                    flipper.removeAllViews()
                    flipper.addView(getImageView(File(it[currentIndex])))
                    flipper.showNext()
                } else {
                    currentIndex--
                    toast("已经到最后一页")
                }
                teaching_book_unit_page_index.text = (currentIndex + 1).toString()
                VariablesXue.xueContext?.currentPageIndex = currentIndex
                teaching_book_unit_progress.secondaryProgress = currentIndex + 1
            }
            return true
        } else if (e1.x - e2.x < -120) {
            flipper.inAnimation = AnimationUtils.loadAnimation(context, R.anim.push_right_in)
            flipper.outAnimation = AnimationUtils.loadAnimation(context, R.anim.push_right_out)
            pageSnapshotPaths?.let {
                currentIndex--
                if (currentIndex >= 0) {
                    flipper.removeAllViews()
                    flipper.addView(getImageView(File(it[currentIndex])))
                    flipper.showPrevious()
                } else {
                    currentIndex++
                    toast("已经到第一页")
                }
                teaching_book_unit_page_index.text = (currentIndex + 1).toString()
                VariablesXue.xueContext?.currentPageIndex = currentIndex
                teaching_book_unit_progress.secondaryProgress = currentIndex + 1
            }
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
//
//class MyAdapter : PagerAdapter() {
//    var mDatas: MutableList<String> = mutableListOf()
//    private var mViews: List<SubsamplingScaleImageView> = mutableListOf()
//
//    override fun getCount(): Int {
//        return mDatas.size
//    }
//
//    override fun isViewFromObject(view: View, obj: Any): Boolean {
//        return view === obj
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//
//        val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
//            ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT
//        )
//
//        val i = position % 4
//        val imageView: SubsamplingScaleImageView = mViews.get(i)
//        imageView.setLayoutParams(params)
//
//        val url = mDatas[position]
//        imageView.setImage(ImageSource.uri(url))
//
//        container.addView(imageView)
//        return imageView
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        val i = position % 4
//        val imageView: SubsamplingScaleImageView = mViews.get(i)
//        if (imageView != null) {
//            imageView.recycle()
//        }
//
//        container.removeView(imageView)
//
//    }
//
//}
