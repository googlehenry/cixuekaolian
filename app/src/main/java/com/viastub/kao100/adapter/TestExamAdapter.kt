package com.viastub.kao100.adapter

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.beans.TestPaperTag
import com.viastub.kao100.db.ExamSimulation
import com.zhy.view.flowlayout.TagFlowLayout

class TestExamAdapter :
    BaseQuickAdapter<ExamSimulation, BaseViewHolder>(R.layout.fragment_kao_item_test_pater_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ExamSimulation) {
        holder.setText(R.id.test_paper_title, item.name)

        item.tags()?.let {
            var tagsHolder =
                holder.getView<TagFlowLayout>(R.id.tag_flow_layout)
            var data = it.mapIndexed { index, tag ->
                TestPaperTag(index + 1, tag)
            }.toMutableList()
            tagsHolder.adapter = TestExamTagAdapter(context, data)
        }


        var paperHolder = holder.getView<LinearLayout>(R.id.paper_holder)
        paperHolder.setTag(R.id.paper_holder, item)
        paperHolder.setOnClickListener { null }
    }

}