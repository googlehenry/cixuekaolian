package com.viastub.kao100.adapter

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.TeachingBookWord

class WordLineAdapter(var onItemClickListener: View.OnClickListener) :
    BaseQuickAdapter<TeachingBookWord, BaseViewHolder>(R.layout.fragment_xue_item_word_line),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: TeachingBookWord) {
        holder.setText(R.id.word_level, item.importance)
        holder.setText(R.id.word_spelling, item.name)
        holder.setText(R.id.word_pronunciation, item.pronunciation)
        holder.setText(R.id.word_grammertype, item.grammerType)
        holder.setText(R.id.word_meaning, item.meaning)


        var wordLevel = holder.getView<TextView>(R.id.word_level)
        if (item.importance == "重点") {
            wordLevel.setTextColor(Color.parseColor("#ff0000"))
        } else {
            wordLevel.setTextColor(Color.parseColor("#000000"))
        }

        var bookItemHolder = holder.getView<LinearLayout>(R.id.word_line_holder)
        bookItemHolder.setTag(R.id.word_line_holder, item)
        bookItemHolder.setOnClickListener {
            onItemClickListener.onClick(it)
        }
    }

}