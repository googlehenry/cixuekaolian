package com.viastub.kao100.adapter

import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.beans.SearchedWord

class SearchedWordAdapter(var itemClickListener: View.OnClickListener) :
    BaseQuickAdapter<SearchedWord, BaseViewHolder>(R.layout.fragment_ci_item_searched_word) {

    override fun convert(holder: BaseViewHolder, item: SearchedWord) {
        //fromHtml，因为搜索结果中的title中含有html标签
//        holder.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/english.ttf"));
        holder.setText(R.id.ci_word, item.word)
        var searchItemHolder = holder.getView<LinearLayout>(R.id.searched_word_item_holder_root)
        searchItemHolder.setTag(R.id.searched_word_item_holder_root, item)
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