package com.qianli.cixuekaolian.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.TeachItem

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ExpandableTextAdapter :
    BaseQuickAdapter<TeachItem, BaseViewHolder>(R.layout.wiget_teach_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: TeachItem) {
        var content = holder.getView<ExpandableTextView>(R.id.expand_text_view)
        var title = holder.getView<TextView>(R.id.title)

        title.text = item.id.toString() + "." + item.title
        content.text = item.content

    }

}