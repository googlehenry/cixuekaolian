package com.viastub.kao100.adapter

import android.view.View
import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.PracticeSection

class TestSectionAdapter(var itemClickListener: View.OnClickListener) :
    BaseQuickAdapter<PracticeSection, BaseViewHolder>(R.layout.activity_kao_exam_section_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: PracticeSection) {
        holder.setText(R.id.section_seq, item.id.toString())
        holder.setText(
            R.id.section_title,
            "${item.name}(总分:${item.score}分,时间:${item.totalTimeInMinutes}分钟)"
        )

        var sectionHolder = holder.getView<CardView>(R.id.section_item_holder)
        sectionHolder.setTag(R.id.section_item_holder, item)
        sectionHolder.setOnClickListener { null }
//        examHolder.setOnTouchListener { view: View, motionEvent: MotionEvent ->
//            when (motionEvent.action) {
//                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> examHolder.setBackgroundColor(
//                    Color.LTGRAY
//                )
//                MotionEvent.ACTION_UP -> {
//                    examHolder.setBackgroundColor(Color.WHITE)
//                    itemClickListener.onClick(view)
//                }
//                else -> examHolder.setBackgroundColor(Color.WHITE)
//            }
//            true
//        }
    }

}