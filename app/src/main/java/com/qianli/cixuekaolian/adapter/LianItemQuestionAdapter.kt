package com.qianli.cixuekaolian.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.LianItemQuestion

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class LianItemQuestionAdapter() :
    BaseQuickAdapter<LianItemQuestion, BaseViewHolder>(R.layout.fragment_lian_item_queston_selection_single),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: LianItemQuestion) {
        holder.setText(R.id.lian_item_question_main_seq, item.id.toString())
        holder.setText(R.id.lian_item_question_main_text, item.questionMainText)


        var questionHolder =
            holder.getView<RecyclerView>(R.id.recycler_lian_item_question_options_holder)

        item.optionLians?.let {
            var adapter = LianQuestionOptionAdapter()
            adapter.data = it
            questionHolder.adapter = adapter
            if (item.questionMainText == null) {
                questionHolder.layoutManager = GridLayoutManager(context, 2)
            } else {
                questionHolder.layoutManager = LinearLayoutManager(context)
            }
        }
        questionHolder.setTag(R.id.recycler_lian_item_question_options_holder, item)
//        questionHolder.setOnClickListener { null }

    }

}