package com.qianli.cixuekaolian.adapter

import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.BookItem

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class BookItemAdapter(var itemClickListener: View.OnClickListener) :
    BaseQuickAdapter<BookItem, BaseViewHolder>(R.layout.fragment_xue_book_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: BookItem) {
        //fromHtml，因为搜索结果中的title中含有html标签
        holder.setText(R.id.book_title, item.title)
        holder.setImageResource(R.id.book_cover_image, R.drawable.demo_eng_pep_3_1)
        var bookItemHolder = holder.getView<LinearLayout>(R.id.book_item_holder)
        bookItemHolder.setTag(R.id.book_item_holder, item)
        bookItemHolder.setOnClickListener(itemClickListener)
    }

}