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


class XuePage1FragmentStudy(var pageSnapshotPaths: MutableList<String>?) : BaseFragment() {

    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_study
    }

    var currentIndex = -1


    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {

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
        val iv = ZoomImageView(context)
        iv.setImageURI(Uri.fromFile(imageFile))
        return iv
    }

    override fun touchEventAware(): Boolean {
        return true
    }

}
