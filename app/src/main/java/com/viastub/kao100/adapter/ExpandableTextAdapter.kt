package com.viastub.kao100.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.viastub.kao100.R
import com.viastub.kao100.db.TeachingPoint

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ExpandableTextAdapter :
    BaseQuickAdapter<TeachingPoint, BaseViewHolder>(R.layout.fragment_xue_item_teach_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: TeachingPoint) {
        var content = holder.getView<ExpandableTextView>(R.id.expand_text_view)
        var title = holder.getView<TextView>(R.id.title_high)

        title.text = item.sequence.toString() + " " + item.point
        content.text = item.explained

    }

}