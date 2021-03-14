package com.qianli.cixuekaolian.adapter

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.ExcerciseByType

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ExcerciseByTypeAdapter() :
    BaseQuickAdapter<ExcerciseByType, BaseViewHolder>(R.layout.fragment_lian_group_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ExcerciseByType) {
        holder.setText(R.id.nav_group_seq, item.id.toString())
        holder.setText(R.id.nav_group_title, item.shortName)
        holder.setText(R.id.nav_group_total, item.total.toString())

        var bookItemHolder = holder.getView<LinearLayout>(R.id.nav_group_holder)
        bookItemHolder.setTag(R.id.nav_group_holder, item)
        bookItemHolder.setOnClickListener { null }
    }

}