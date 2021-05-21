package com.viastub.kao100.adapter

import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
        holder.setText(
            R.id.test_paper_title,
            "${item.name}"
        )
        holder.setText(
            R.id.test_paper_last_score,
            "[${item.myExamSimuHistory?.myScores?.toString() ?: 0}分]"
        )
        var paperTitle =
            holder.getView<TextView>(R.id.test_paper_title)
        //(${if (!item.downloaded) "未下载" else "本地"})
        var downloadedImg = holder.getView<ImageView>(R.id.test_paper_download)
        if (item.downloaded) {
            downloadedImg.setImageResource(R.drawable.ic_icon_saved)
        } else {
            downloadedImg.setImageResource(R.drawable.ic_floating_buttons_sync_from_server)
        }

        item.tags()?.let {
            var tagsHolder =
                holder.getView<TagFlowLayout>(R.id.tag_flow_layout)
            var data = it?.filter { it.isNotBlank() }.mapIndexed { index, tag ->
                TestPaperTag(index + 1, tag)
            }.toMutableList()
            tagsHolder.adapter = SimpleTagAdapter(context, data)
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