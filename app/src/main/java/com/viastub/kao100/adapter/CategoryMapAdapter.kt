package com.viastub.kao100.adapter

import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.module.my.CategoryMap

class CategoryMapAdapter(var itemClickListener: View.OnClickListener) :
    BaseQuickAdapter<CategoryMap, BaseViewHolder>(R.layout.my_errorbook_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: CategoryMap) {
        holder.setText(R.id.question_category, item.category)
        var count = "共${item.countTemplates}大题"
        if (item.countQuestions > 0) {
            count += ",共${item.countQuestions}小题"
        }

        holder.setText(R.id.question_count, count)

        var bookItemHolder = holder.getView<LinearLayout>(R.id.question_holder_root)

        bookItemHolder.setTag(R.id.question_holder_root, item)
        bookItemHolder.setOnClickListener {
            itemClickListener.onClick(it)
        }
    }


}


