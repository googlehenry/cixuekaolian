package com.viastub.kao100.adapter

import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.ExamSimulation
import com.viastub.kao100.db.PracticeSection

class TestSectionAdapter(
    var examSimulation: ExamSimulation,
    var itemClickListener: View.OnClickListener
) :
    BaseQuickAdapter<PracticeSection, BaseViewHolder>(R.layout.activity_kao_exam_section_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: PracticeSection) {
        holder.setText(R.id.section_seq, item.displaySeq.toString())
        holder.setText(R.id.section_title, item.name)

        var sectionHolder = holder.getView<CardView>(R.id.section_item_holder)
        sectionHolder.setTag(R.id.section_item_holder, item)
        sectionHolder.setTag(-1, examSimulation)

        sectionHolder.setOnClickListener { null }

        sectionHolder.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> sectionHolder.setBackgroundColor(
                    Color.LTGRAY
                )
                MotionEvent.ACTION_UP -> {
                    sectionHolder.setBackgroundColor(Color.WHITE)
                    itemClickListener.onClick(view)
                }
                else -> sectionHolder.setBackgroundColor(Color.WHITE)
            }
            true
        }
    }

}