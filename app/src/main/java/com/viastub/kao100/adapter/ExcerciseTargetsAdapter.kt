package com.viastub.kao100.adapter

import android.view.View
import androidx.cardview.widget.CardView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.PracticeTarget

class ExcerciseTargetsAdapter(var itemClickListener: View.OnClickListener) :
    BaseQuickAdapter<PracticeTarget, BaseViewHolder>(R.layout.fragment_lian_target_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: PracticeTarget) {
        holder.setText(R.id.nav_target_name, item.name)

        var bookItemHolder = holder.getView<CardView>(R.id.excercise_nav_item_holder)
        bookItemHolder.setBackgroundResource(R.drawable.shape_button_half_rounded)

        bookItemHolder.setTag(R.id.excercise_nav_item_holder, item)
        bookItemHolder.setOnClickListener {
            itemClickListener.onClick(it)
        }
    }


}


