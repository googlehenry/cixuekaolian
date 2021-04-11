package com.viastub.kao100.adapter

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.PracticeBook
import com.viastub.kao100.db.PracticeSection
import com.viastub.kao100.module.lian.OnExcercistStartListener

class ExcerciseByUnitAdapter(
    val excerciseListener: OnExcercistStartListener,
    val book: PracticeBook
) :
    BaseQuickAdapter<PracticeSection, BaseViewHolder>(R.layout.fragment_lian_book_item_unit_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: PracticeSection) {
        holder.setText(R.id.unit_title, item.name.toString())
        var unitTotal = holder.getView<TextView>(R.id.summary_book_unit_total)
        var unitDone = holder.getView<TextView>(R.id.summary_book_unit_done)

        val max = (item?.practiceTemplateIds()?.size ?: 0)
        val done = (item.mySectionPracticeHistory?.myFinishedTemplateIds()?.size ?: 0)

        unitDone.text = "$done"
        unitTotal.text = "/${max}"

        var bookItemHolder = holder.getView<ConstraintLayout>(R.id.unit_line_holder)
        bookItemHolder.setTag(R.id.unit_line_holder, item)
        bookItemHolder.setOnClickListener {
            excerciseListener.start(book, it.getTag(R.id.unit_line_holder) as PracticeSection)
        }
    }

}