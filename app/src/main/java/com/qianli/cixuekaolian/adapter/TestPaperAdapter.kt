package com.qianli.cixuekaolian.adapter

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.TestPaper

class TestPaperAdapter :
    BaseQuickAdapter<TestPaper, BaseViewHolder>(R.layout.fragment_kao_item_test_pater_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: TestPaper) {
        holder.setText(R.id.test_paper_title, item.title)
        var paperHolder = holder.getView<LinearLayout>(R.id.paper_holder)
        paperHolder.setTag(R.id.paper_holder, item)
        paperHolder.setOnClickListener { null }
    }

}