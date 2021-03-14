package com.qianli.cixuekaolian.adapter

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.ExcerciseBook

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ExcerciseBookAdapter(var itemClickListener: View.OnClickListener) :
    BaseQuickAdapter<ExcerciseBook, BaseViewHolder>(R.layout.fragment_lian_nav_book_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ExcerciseBook) {
        holder.setText(R.id.nav_book_title, item.shortName)

        var bookItemHolder = holder.getView<ConstraintLayout>(R.id.excercise_nav_item_holder)
        bookItemHolder.setTag(R.id.excercise_nav_item_holder, item)
        bookItemHolder.setOnClickListener {
            itemClickListener.onClick(it)
        }
    }


}


