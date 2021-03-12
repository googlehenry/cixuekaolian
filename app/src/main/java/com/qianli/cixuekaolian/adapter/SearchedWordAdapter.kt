package com.qianli.cixuekaolian.adapter

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.SearchedWord

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class SearchedWordAdapter :
    BaseQuickAdapter<SearchedWord, BaseViewHolder>(R.layout.fragment_ci_item_searched_word) {

    init {
        addChildClickViewIds(R.id.article_favorite)
    }

    override fun convert(holder: BaseViewHolder, item: SearchedWord) {
        //fromHtml，因为搜索结果中的title中含有html标签
//        holder.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/english.ttf"));
        holder.setText(R.id.ci_word, item.word)
        holder.setText(R.id.ci_source, item.source)
        holder.getView<LinearLayout>(R.id.searched_word_item_holder).setOnClickListener(null)

//        if (item.collect) {
//            Glide.with(context).load(R.drawable.ic_like_checked)
//                .into(holder.getView(R.id.article_favorite))
//        } else {
//            Glide.with(context).load(R.drawable.ic_like_normal)
//                .into(holder.getView(R.id.article_favorite))
//        }
    }

}