package com.qianli.cixuekaolian.adapter

import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.ExcerciseByUnit

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ExcerciseByUnitAdapter() :
    BaseQuickAdapter<ExcerciseByUnit, BaseViewHolder>(R.layout.fragment_lian_group_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ExcerciseByUnit) {
        holder.setText(R.id.nav_group_seq, item.id.toString())
        holder.setText(R.id.nav_group_title, item.shortName)
        holder.setText(R.id.nav_group_error, "已错:" + item.error.toString())
        holder.setText(R.id.nav_group_done, "已做:" + item.done.toString())
        holder.setText(R.id.nav_group_total, "共计:" + item.total.toString())

        var progressBar = holder.getView<ProgressBar>(R.id.nav_group_progress)
        progressBar.max = item.total
        progressBar.progress = item.error
        progressBar.secondaryProgress = item.done

        var bookItemHolder = holder.getView<CardView>(R.id.nav_group_holder)
        bookItemHolder.setTag(R.id.nav_group_holder, item)
        bookItemHolder.setOnClickListener { null }
    }

}