package com.viastub.kao100.adapter

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.beans.TestPaper

class TestPaperAdapter :
    BaseQuickAdapter<TestPaper, BaseViewHolder>(R.layout.fragment_kao_item_test_pater_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: TestPaper) {
        holder.setText(R.id.test_paper_title, item.title)
        item.tags?.let { holder.setText(R.id.test_paper_tags, it.first()) }

        var paperHolder = holder.getView<LinearLayout>(R.id.paper_holder)
        paperHolder.setTag(R.id.paper_holder, item)
        paperHolder.setOnClickListener { null }
    }

}