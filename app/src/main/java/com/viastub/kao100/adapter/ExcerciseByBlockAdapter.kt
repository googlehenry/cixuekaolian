package com.viastub.kao100.adapter

import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.beans.ExcerciseByBlock
import com.viastub.kao100.beans.ExcerciseTarget
import com.viastub.kao100.module.lian.OnExcercistStartListener

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ExcerciseByBlockAdapter(
    val excerciseListener: OnExcercistStartListener,
    val excercistTarget: ExcerciseTarget
) :
    BaseQuickAdapter<ExcerciseByBlock, BaseViewHolder>(R.layout.fragment_lian_block_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ExcerciseByBlock) {
        holder.setText(R.id.nav_block_title, item.shortName)

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
                it.getTag(R.id.nav_group_holder) as ExcerciseByBlock
            )
        }
    }

}