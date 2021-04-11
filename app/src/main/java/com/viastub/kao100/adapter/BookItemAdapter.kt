package com.viastub.kao100.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.TeachingBook

class BookItemAdapter(var itemClickListener: View.OnClickListener) :
    BaseQuickAdapter<TeachingBook, BaseViewHolder>(R.layout.fragment_xue_book_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: TeachingBook) {
        //fromHtml，因为搜索结果中的title中含有html标签
        holder.setText(R.id.book_name, item.name)

        var coverImg = holder.getView<ImageView>(R.id.book_cover_image)
        item.coverImage()?.let { coverImg.setImageURI(Uri.fromFile(it)) }

        var bookItemHolder = holder.getView<LinearLayout>(R.id.book_item_holder)
        bookItemHolder.setTag(R.id.book_item_holder, item)
        bookItemHolder.setOnClickListener(itemClickListener)
    }

}