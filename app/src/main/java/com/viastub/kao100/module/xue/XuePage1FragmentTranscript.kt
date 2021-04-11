package com.viastub.kao100.module.xue

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.TranscriptItemAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.db.TeachingTranslation
import kotlinx.android.synthetic.main.activity_xue_detail_page_frag_transcript.*

class XuePage1FragmentTranscript(var bookTranslationsDb: MutableList<TeachingTranslation>?) :
    BaseFragment(), View.OnClickListener {

    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_transcript
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        bookTranslationsDb?.let {
            var adapter = TranscriptItemAdapter(this)
            adapter.data = it.mapIndexed { index, trans ->
                trans.sequence = index + 1
                trans
            }.toMutableList()
            recycler_view_transcipt.adapter = adapter
        }

        //https://www.jianshu.com/p/e68a0b5fd383
        recycler_view_transcipt.addItemDecoration(
            DividerItemDecoration(
                mContext,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onClick(v: View?) {
    }

}