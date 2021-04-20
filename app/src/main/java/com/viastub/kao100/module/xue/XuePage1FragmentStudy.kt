package com.viastub.kao100.module.xue

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.utils.VariablesXue
import com.viastub.kao100.wigets.ZoomImageView
import kotlinx.android.synthetic.main.activity_xue_detail_page_frag_study.*
import java.io.File


class XuePage1FragmentStudy(
    var progressUpdatedListener: ProgressUpdatedListener,
    var pageSnapshotPaths: MutableList<String>?
) : BaseFragment() {

    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_study
    }


    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {

        VariablesXue.xueContext?.currentPageIndex = 0
        pageSnapshotPaths?.let {
            loadPage(0)
        }

        teaching_book_unit_page_next.setOnClickListener {
//            flipper.inAnimation = AnimationUtils.loadAnimation(context, R.anim.push_left_in)
//            flipper.outAnimation = AnimationUtils.loadAnimation(context, R.anim.push_left_out)
            pageSnapshotPaths?.let {
                var currentIndex = (VariablesXue.xueContext?.currentPageIndex ?: 0) + 1
                if (currentIndex < it.size) {
                    flipper.removeAllViews()
                    flipper.addView(getImageView(File(it[currentIndex])))
                    flipper.showNext()
                } else {
                    currentIndex--
                    toast("已经到最后一页")
                }
                VariablesXue.xueContext?.currentPageIndex = currentIndex
                progressUpdatedListener.updateProgress(
                    currentIndex + 1,
                    0,
                    pageSnapshotPaths!!.size
                )
            }
        }
        teaching_book_unit_page_prev.setOnClickListener {
//            flipper.inAnimation = AnimationUtils.loadAnimation(context, R.anim.push_right_in)
//            flipper.outAnimation = AnimationUtils.loadAnimation(context, R.anim.push_right_out)
            pageSnapshotPaths?.let {
                var currentIndex = (VariablesXue.xueContext?.currentPageIndex ?: 0) - 1
                if (currentIndex >= 0) {
                    flipper.removeAllViews()
                    flipper.addView(getImageView(File(it[currentIndex])))
                    flipper.showPrevious()
                } else {
                    currentIndex++
                    toast("已经到第一页")
                }
                VariablesXue.xueContext?.currentPageIndex = currentIndex
                progressUpdatedListener.updateProgress(
                    currentIndex + 1,
                    0,
                    pageSnapshotPaths!!.size
                )
            }
        }

    }

    fun loadPage(idx: Int) {
        pageSnapshotPaths?.let {
            VariablesXue.xueContext?.currentPageIndex = idx
            progressUpdatedListener.updateProgress(idx + 1, 0, pageSnapshotPaths!!.size)
            flipper.removeAllViews()
            flipper.addView(getImageView(File(it[idx])))
            flipper.showNext()
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
