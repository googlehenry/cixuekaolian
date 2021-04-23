package com.viastub.kao100.adapter

import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.beans.TestPaperTag
import com.viastub.kao100.db.MyCollectedNote
import com.zhy.view.flowlayout.TagFlowLayout

class MyCollectItemAdapter(private val itemClickListener: View.OnClickListener) :
    BaseQuickAdapter<MyCollectedNote, BaseViewHolder>(R.layout.my_collection_item) {


    override fun convert(holder: BaseViewHolder, item: MyCollectedNote) {
        //fromHtml，因为搜索结果中的title中含有html标签
        holder.setText(R.id.note_seq, item.id.toString())
        holder.setText(R.id.note_collect, item.collectedText)
        holder.setText(R.id.note_meta, item.dateAdded)

        item.tags?.let { tagStr ->
            var tts = tagStr.split(",")
            var tagsHolder =
                holder.getView<TagFlowLayout>(R.id.tag_flow_layout)
            var data = tts.mapIndexed { index, tag ->
                TestPaperTag(index + 1, tag)
            }.toMutableList()
            tagsHolder.adapter = SimpleTagAdapter(context, data)
        }


        var searchItemHolder = holder.getView<LinearLayout>(R.id.note_collect_holder_root)
        searchItemHolder.setTag(R.id.note_collect_holder_root, item)
        searchItemHolder.setOnClickListener(null)

        searchItemHolder.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> searchItemHolder.setBackgroundColor(
                    Color.LTGRAY
                )
                MotionEvent.ACTION_UP -> {
                    searchItemHolder.setBackgroundColor(Color.WHITE)
                    itemClickListener.onClick(view)
                }
                else -> searchItemHolder.setBackgroundColor(Color.WHITE)
            }
            true
        }

    }
}