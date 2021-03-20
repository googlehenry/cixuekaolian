package com.qianli.cixuekaolian.adapter

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.LianQuestionOption

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class LianQuestionOptionAdapter() :
    BaseQuickAdapter<LianQuestionOption, BaseViewHolder>(R.layout.fragment_lian_item_queston_option_text),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: LianQuestionOption) {
        holder.setText(R.id.lian_item_option_main, item.optionMainText)


        var bookItemHolder = holder.getView<LinearLayout>(R.id.lian_item_option_holder)
        bookItemHolder.setTag(R.id.lian_item_option_holder, item)
        bookItemHolder.setOnClickListener { null }
    }

}