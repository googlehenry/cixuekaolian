package com.qianli.cixuekaolian.adapter

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.ExcerciseByType
import com.qianli.cixuekaolian.beans.ExcerciseTarget
import com.qianli.cixuekaolian.module.lian.OnExcercistStartListener

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ExcerciseByTypeAdapter(
    val excerciseListener: OnExcercistStartListener,
    val excercistTarget: ExcerciseTarget
) :
    BaseQuickAdapter<ExcerciseByType, BaseViewHolder>(R.layout.fragment_lian_group_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ExcerciseByType) {
        holder.setText(R.id.nav_group_seq, item.id.toString())
        holder.setText(R.id.nav_group_title, item.shortName)
        holder.setText(R.id.nav_group_error, "错:" + item.error.toString())
        holder.setText(R.id.nav_group_done, "做:" + item.done.toString())
        holder.setText(R.id.nav_group_total, "共:" + item.total.toString())

        var groupIcon = holder.getView<ImageView>(R.id.nav_lian_group_icon)
        groupIcon.visibility = View.GONE

        var expandIcon = holder.getView<ImageView>(R.id.excercise_expand_collapse)
//        expandIcon.visibility = View.GONE
        expandIcon.setImageResource(R.drawable.icon_richeng_time_start)
        var progressBar = holder.getView<ProgressBar>(R.id.nav_group_progress)
        progressBar.max = item.total
        progressBar.progress = item.error
        progressBar.secondaryProgress = item.done


//        expandIcon.setTag(R.id.icon_richeng_time_start, item)
//        expandIcon.setOnClickListener {
//            excerciseListener.start(
//                excercistTarget,
//                it.getTag(R.id.nav_group_holder) as ExcerciseByType
//            )
//        }


        var bookItemHolder = holder.getView<CardView>(R.id.nav_group_holder)
        bookItemHolder.setTag(R.id.nav_group_holder, item)
        bookItemHolder.setOnClickListener {
            excerciseListener.start(
                excercistTarget,
                it.getTag(R.id.nav_group_holder) as ExcerciseByType
            )
        }
    }

}