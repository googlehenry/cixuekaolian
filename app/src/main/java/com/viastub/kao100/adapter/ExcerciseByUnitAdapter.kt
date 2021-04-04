package com.viastub.kao100.adapter

import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.beans.ExcerciseByBook
import com.viastub.kao100.beans.ExcerciseByUnit
import com.viastub.kao100.module.lian.OnExcercistStartListener

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ExcerciseByUnitAdapter(
    val excerciseListener: OnExcercistStartListener,
    val book: ExcerciseByBook
) :
    BaseQuickAdapter<ExcerciseByUnit, BaseViewHolder>(R.layout.fragment_lian_nav_item_units_in_book),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ExcerciseByUnit) {
        holder.setText(R.id.unit_title, item.shortName.toString())
        holder.setText(R.id.nav_unit_error, "(" + item.error.toString())
        holder.setText(R.id.nav_unit_done, "/" + item.done.toString())
        holder.setText(R.id.nav_unit_total, "/" + item.total.toString() + ")")

        var bookItemHolder = holder.getView<ConstraintLayout>(R.id.unit_line_holder)
        bookItemHolder.setTag(R.id.unit_line_holder, item)
        bookItemHolder.setOnClickListener {
            excerciseListener.start(book, it.getTag(R.id.unit_line_holder) as ExcerciseByUnit)
        }
    }

}