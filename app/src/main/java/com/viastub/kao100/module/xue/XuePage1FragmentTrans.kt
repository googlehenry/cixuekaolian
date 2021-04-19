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
        VariablesXue.xueContext?.currentPageIndex?.let { idx ->
            loadPage(idx)
        }


        teaching_book_unit_page_next.setOnClickListener {
            bookTranslationsDb?.let {
                VariablesXue.xueContext?.currentPageIndex?.let { idx ->
                    var currentIndex = idx + 1
                    if (currentIndex < 0) currentIndex = 0
                    if (currentIndex > it.size - 1) currentIndex = it.size - 1
                    loadPage(currentIndex)
                }
            }
        }
        teaching_book_unit_page_prev.setOnClickListener {
            bookTranslationsDb?.let {
                VariablesXue.xueContext?.currentPageIndex?.let { idx ->
                    var currentIndex = idx - 1
                    if (currentIndex < 0) currentIndex = 0
                    if (currentIndex > it.size - 1) currentIndex = it.size - 1
                    loadPage(currentIndex)
                }
            }
        }
    }

    override fun onClick(v: View?) {
    }

    fun loadPage(index: Int) {
        bookTranslationsDb?.let {
            if (index < it.size) {
                var oneTrans = it[index]
                oneTrans.sequence = index + 1
                var adapter = TranscriptItemAdapter(showEngText)
                adapter.data = mutableListOf(oneTrans)
                recycler_view_transcipt.adapter = adapter
                pageLoadedMap[index] = true
                teaching_book_unit_progress.secondaryProgress = index + 1
                teaching_book_unit_progress.max = it.size
            }
            teaching_book_unit_progress.secondaryProgress = index + 1
            teaching_book_unit_progress.max = it.size
            VariablesXue.xueContext?.currentPageIndex = index
        }

    }

}


