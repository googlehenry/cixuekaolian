package com.viastub.kao100.adapter

import android.view.View
import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.beans.ExcerciseTarget

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ExcerciseTargetsAdapter(var itemClickListener: View.OnClickListener) :
    BaseQuickAdapter<ExcerciseTarget, BaseViewHolder>(R.layout.fragment_lian_nav_target_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ExcerciseTarget) {
        holder.setText(R.id.nav_target_name, item.shortName)

        var bookItemHolder = holder.getView<CardView>(R.id.excercise_nav_item_holder)
        bookItemHolder.setBackgroundResource(R.drawable.shape_button_half_rounded)

        bookItemHolder.setTag(R.id.excercise_nav_item_holder, item)
        bookItemHolder.setOnClickListener {
            itemClickListener.onClick(it)
        }
    }


}


