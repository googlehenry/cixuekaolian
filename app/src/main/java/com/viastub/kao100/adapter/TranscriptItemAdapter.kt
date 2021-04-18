package com.viastub.kao100.adapter

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.TeachingTranslation
import com.viastub.kao100.wigets.TextViewSelectionCallback


class TranscriptItemAdapter(
    var showEngText: Boolean = false
) :
    BaseQuickAdapter<TeachingTranslation, BaseViewHolder>(R.layout.fragment_xue_item_transcript_line),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: TeachingTranslation) {
        //fromHtml，因为搜索结果中的title中含有html标签
        holder.setText(R.id.transcript_seq, item.sequence.toString())
        if (showEngText) {
            holder.setText(R.id.transcript_english, item.text_eng)
        } else {
            holder.setText(R.id.transcript_chinese, item.text_zh)
        }
        val engText = holder.getView<TextView>(R.id.transcript_english)
        val zhText = holder.getView<TextView>(R.id.transcript_chinese)

        var bookItemHolder = holder.getView<ConstraintLayout>(R.id.transcript_holder)
        bookItemHolder.setTag(R.id.transcript_holder, item)
        bookItemHolder.setOnClickListener(null)

        engText.customSelectionActionModeCallback = TextViewSelectionCallback(context, engText)
        zhText.customSelectionActionModeCallback = TextViewSelectionCallback(context, zhText)
    }

}