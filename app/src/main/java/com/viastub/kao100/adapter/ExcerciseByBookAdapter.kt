package com.viastub.kao100.adapter

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.beans.ExcerciseByBook
import com.viastub.kao100.beans.ExcerciseTarget
import com.viastub.kao100.module.lian.OnExcercistStartListener

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ExcerciseByBookAdapter(
    var itemClickListener: View.OnClickListener,
    val excerciseListener: OnExcercistStartListener, val excercistTarget: ExcerciseTarget
) :
    BaseQuickAdapter<ExcerciseByBook, BaseViewHolder>(R.layout.fragment_lian_group_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ExcerciseByBook) {
        holder.setText(R.id.nav_group_seq, item.id.toString())
        holder.setText(R.id.nav_group_title, item.shortName)
        holder.setBackgroundResource(R.id.nav_lian_group_icon, R.drawable.demo_eng_pep_3_1_snippet)
        holder.setText(R.id.nav_group_error, "错:" + item.error.toString())
        holder.setText(R.id.nav_group_done, "做:" + item.done.toString())
        holder.setText(R.id.nav_group_total, "共:" + item.total.toString())

        var groupIcon = holder.getView<ImageView>(R.id.nav_lian_group_icon)
        groupIcon.visibility = View.VISIBLE

        var progressBar = holder.getView<ProgressBar>(R.id.nav_group_progress)
        progressBar.max = item.total
        progressBar.progress = item.error
        progressBar.secondaryProgress = item.done

        item.units?.let {
            var recyclerViewUnits = holder.getView<RecyclerView>(R.id.recycler_units)
            val unitAdapter = ExcerciseByUnitAdapter(excerciseListener, item)
            unitAdapter.data = it
            recyclerViewUnits.adapter = unitAdapter
            recyclerViewUnits.layoutManager = GridLayoutManager(context, 2)
        }


        var bookItemHolder = holder.getView<CardView>(R.id.nav_group_holder)
        bookItemHolder.setTag(R.id.nav_group_holder, item)
        bookItemHolder.setOnClickListener { itemClickListener.onClick(it) }
    }

}