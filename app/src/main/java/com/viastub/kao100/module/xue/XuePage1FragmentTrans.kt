package com.viastub.kao100.module.xue

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.TranscriptItemAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.db.TeachingTranslation
import com.viastub.kao100.utils.VariablesXue
import kotlinx.android.synthetic.main.activity_xue_detail_page_frag_transcript.*

class XuePage1FragmentTrans(
    var bookTranslationsDb: MutableList<TeachingTranslation>?,
    var showEngText: Boolean = false
) :
    BaseFragment(), View.OnClickListener {

    var pageLoadedMap: MutableMap<Int, Boolean> = mutableMapOf()


    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_transcript
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
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

    override fun onResume() {
        super.onResume()
        VariablesXue.xueContext?.currentPageIndex?.let { index ->
            if (index >= 0) {
                bookTranslationsDb?.let {
                    if (index < it.size) {
                        if (pageLoadedMap[index] == null) {
                            var adapter = TranscriptItemAdapter(this, showEngText)
                            var oneTrans = it[index]
                            oneTrans.sequence = index + 1
                            adapter.data = mutableListOf(oneTrans)
                            recycler_view_transcipt.adapter = adapter
                            pageLoadedMap[index] = true
                            if (index > 0) {
                                toast("当前第${index + 1}页已加载")
                            }
                        }
                    }

                }

            }
        }
    }

}