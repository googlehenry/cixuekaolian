package com.viastub.kao100.module.xue

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.utils.VariablesXue
import com.viastub.kao100.wigets.ZoomImageView
import kotlinx.android.synthetic.main.activity_xue_detail_page_frag_study.*
import java.io.File


class XuePage1FragmentStudy(var pageSnapshotPaths: MutableList<String>?) : BaseFragment() {

    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_study
    }

    var currentIndex = -1


    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {

        pageSnapshotPaths?.let {
            currentIndex = 0
            VariablesXue.xueContext?.currentPageIndex = currentIndex

            teaching_book_unit_progress.max = it.size
            teaching_book_unit_progress.secondaryProgress = currentIndex + 1
            flipper.addView(getImageView(File(it[currentIndex])));
        }

        teaching_book_unit_page_next.setOnClickListener {
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
                VariablesXue.xueContext?.currentPageIndex = currentIndex
                teaching_book_unit_progress.secondaryProgress = currentIndex + 1
            }
        }
        teaching_book_unit_page_prev.setOnClickListener {
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
                VariablesXue.xueContext?.currentPageIndex = currentIndex
                teaching_book_unit_progress.secondaryProgress = currentIndex + 1
            }
        }

    }


    private fun getImageView(imageFile: File): View {
        val iv = ZoomImageView(context)
        iv.setImageURI(Uri.fromFile(imageFile))
        return iv
    }

    override fun touchEventAware(): Boolean {
        return true
    }

}
