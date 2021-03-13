package com.qianli.cixuekaolian.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.TranscriptItem

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class TranscriptItemAdapter(var itemClickListener: View.OnClickListener) :
    BaseQuickAdapter<TranscriptItem, BaseViewHolder>(R.layout.fragment_xue_item_transcript_line),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: TranscriptItem) {
        //fromHtml，因为搜索结果中的title中含有html标签
        holder.setText(R.id.transcript_seq, item.seq)
        holder.setText(R.id.transcript_english, item.transcriptEnglish)
        holder.setText(R.id.transcript_chinese, item.transcriptChinese)
        val engText = holder.getView<TextView>(R.id.transcript_english)
        val zhText = holder.getView<TextView>(R.id.transcript_chinese)
        if (item.title) {
            val weight = Typeface.defaultFromStyle(Typeface.BOLD)
            engText.typeface = weight
            zhText.typeface = weight
            engText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            zhText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            engText.setTextColor(Color.parseColor("#673AB7"))
            zhText.setTextColor(Color.parseColor("#673AB7"))
        } else {
            val weight = Typeface.defaultFromStyle(Typeface.NORMAL)
            engText.typeface = weight
            zhText.typeface = weight
            engText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            zhText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            engText.setTextColor(Color.parseColor("#212121"))
            zhText.setTextColor(Color.parseColor("#087E19"))
        }
        var bookItemHolder = holder.getView<ConstraintLayout>(R.id.transcript_holder)
        bookItemHolder.setTag(R.id.book_item_holder, item)
        bookItemHolder.setOnClickListener(null)
    }

}