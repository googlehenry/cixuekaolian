package com.viastub.kao100.adapter

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.beans.TemplateIDStatus

class TemplateIDPairAdapter(var onItemClickListener: View.OnClickListener) :
    BaseQuickAdapter<TemplateIDStatus, BaseViewHolder>(R.layout.lian_activity_template_id_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: TemplateIDStatus) {
        holder.setText(R.id.template_seq, item.seq.toString())//seq

        var len = item.shortCategory?.let {
            if (it.length > 4) 4 else it.length
        } ?: 0

        item.shortCategory?.let {
            holder.setText(
                R.id.template_cat,
                item.shortCategory?.substring(0, len)
            )
        }
        var template_seq_view = holder.getView<TextView>(R.id.template_seq)

        if (item.finished) {
            template_seq_view.setBackgroundResource(R.drawable.shape_red_round_cycle_solid)
        } else {
            template_seq_view.setBackgroundResource(R.drawable.shape_blue_round_cycle_solid)
        }


        var bookItemHolder = holder.getView<LinearLayout>(R.id.template_seq_holder)
        bookItemHolder.setTag(R.id.template_seq_holder, item)
        bookItemHolder.setOnClickListener {
            onItemClickListener.onClick(it)
        }
    }

}