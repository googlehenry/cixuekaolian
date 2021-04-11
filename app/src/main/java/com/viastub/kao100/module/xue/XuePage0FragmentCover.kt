package com.viastub.kao100.module.xue

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.db.TeachingBookUnitSection
import kotlinx.android.synthetic.main.activity_xue_chapter_page_frag_cover.*

class XuePage0FragmentCover(var unitDb: TeachingBookUnitSection) : BaseFragment() {

    override fun id(): Int {
        return R.layout.activity_xue_chapter_page_frag_cover
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        unitDb.coverImage()?.let {
            unit_cover_image.setImageURI(Uri.fromFile(it))
        }
    }

}