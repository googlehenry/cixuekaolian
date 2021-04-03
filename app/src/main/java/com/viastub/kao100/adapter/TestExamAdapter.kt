package com.viastub.kao100.adapter

import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.beans.TestPaperTag
import com.viastub.kao100.db.ExamSimulation
import com.zhy.view.flowlayout.TagFlowLayout

class TestExamAdapter(var itemClickListener: View.OnClickListener) :
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


        var examHolder = holder.getView<LinearLayout>(R.id.paper_holder)
        examHolder.setTag(R.id.paper_holder, item)
        examHolder.setOnClickListener { null }
        examHolder.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> examHolder.setBackgroundColor(
                    Color.LTGRAY
                )
                MotionEvent.ACTION_UP -> {
                    examHolder.setBackgroundColor(Color.WHITE)
                    itemClickListener.onClick(view)
                }
                else -> examHolder.setBackgroundColor(Color.WHITE)
            }
            true
        }
    }

}