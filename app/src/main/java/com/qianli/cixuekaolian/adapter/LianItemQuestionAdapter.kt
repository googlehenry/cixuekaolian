package com.qianli.cixuekaolian.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.LianItem
import com.qianli.cixuekaolian.beans.LianItemQuestion
import com.qianli.cixuekaolian.beans.LianItemQuestionType

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class LianItemQuestionAdapter(var lianItem: LianItem, var questionsHolder: RecyclerView) :
    BaseQuickAdapter<LianItemQuestion, BaseViewHolder>(R.layout.fragment_lian_item_queston_selection_single),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: LianItemQuestion) {
        holder.setText(R.id.lian_item_question_main_seq, item.id.toString())
        holder.setText(R.id.lian_item_question_main_text, item.questionMainText)

        var questionMainText = holder.getView<TextView>(R.id.lian_item_question_main_text)
        questionMainText.visibility = if (item.questionMainText == null) View.GONE else View.VISIBLE

        var questionHolder =
            holder.getView<RecyclerView>(R.id.recycler_lian_item_question_options_holder)

        item.optionLians?.let {
            var adapter = LianQuestionOptionAdapter(item, questionHolder, lianItem, questionHolder)
            adapter.data = it
            questionHolder.adapter = adapter
            when (item.type) {
                LianItemQuestionType.SELECT_ONE_LEN1 -> questionHolder.layoutManager =
                    GridLayoutManager(context, 8)
                LianItemQuestionType.SELECT_ONE_LEN2 -> questionHolder.layoutManager =
                    GridLayoutManager(context, 4)
                LianItemQuestionType.SELECT_ONE_LEN10 -> questionHolder.layoutManager =
                    GridLayoutManager(context, 2)
                else -> questionHolder.layoutManager = LinearLayoutManager(context)
            }
        }

        item.answerLians?.let {
            var adapter = LianQuestionAnswerAdapter(item, questionHolder, lianItem, questionHolder)
            adapter.data = it
            questionHolder.adapter = adapter
            when (item.type) {
                LianItemQuestionType.FILL_ONE_LEN10 -> questionHolder.layoutManager =
                    GridLayoutManager(context, 2)
                else -> questionHolder.layoutManager = LinearLayoutManager(context)
            }
        }


        questionHolder.setTag(R.id.recycler_lian_item_question_options_holder, item)


    }

}