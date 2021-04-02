package com.viastub.kao100.adapter

import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.beans.WordLine

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class WordLineAdapter :
    BaseQuickAdapter<WordLine, BaseViewHolder>(R.layout.fragment_xue_item_word_line),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: WordLine) {
        holder.setText(R.id.word_line, item.wordLine)

        var bookItemHolder = holder.getView<ConstraintLayout>(R.id.word_line_holder)
        bookItemHolder.setTag(R.id.word_line_holder, item)
        bookItemHolder.setOnClickListener(null)
    }

}