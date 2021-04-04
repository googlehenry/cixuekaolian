package com.viastub.kao100.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.PracticeQuestion
import com.viastub.kao100.db.PracticeQuestionTemplate
import com.viastub.kao100.utils.Constants

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class LianItemQuestionAdapter(
    var lianItem: PracticeQuestionTemplate,
    var questionsHolder: RecyclerView
) :
    BaseQuickAdapter<PracticeQuestion, BaseViewHolder>(R.layout.fragment_lian_item_queston_selection_single),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: PracticeQuestion) {
        holder.setText(R.id.lian_item_question_main_seq, item.id.toString())
        holder.setText(R.id.lian_item_question_main_text, item.text)
        holder.setText(R.id.lian_item_question_answer_reviewed, item.answerStandard)
        holder.setText(R.id.lian_item_question_answer_explained, item.answerKeyPoints)

        var questionMainText = holder.getView<TextView>(R.id.lian_item_question_main_text)
        questionMainText.visibility = if (item.text == null) View.GONE else View.VISIBLE

        var questionHolder =
            holder.getView<RecyclerView>(R.id.recycler_lian_item_question_options_holder)



        item.optionsDb?.let {
            if (item.type == Constants.practice_question_type_fill) {
                var adapter =
                    LianQuestionAnswerAdapter(item, questionHolder, lianItem, questionHolder)
                adapter.data = it
                questionHolder.adapter = adapter
                questionHolder.layoutManager = GridLayoutManager(context, item.layoutOptionsPerRow)
            } else if (item.type == Constants.practice_question_type_select) {
                var adapter =
                    LianQuestionOptionAdapter(item, questionHolder, lianItem, questionHolder)
                adapter.data = it
                questionHolder.adapter = adapter
                questionHolder.layoutManager = GridLayoutManager(context, item.layoutOptionsPerRow)
            }
        }

        var answerReviewed = holder.getView<TextView>(R.id.lian_item_question_answer_reviewed)
        var answerExplained = holder.getView<TextView>(R.id.lian_item_question_answer_explained)

        answerReviewed.visibility = if (lianItem.submitted) View.VISIBLE else View.GONE
        answerExplained.visibility = if (lianItem.submitted) View.VISIBLE else View.GONE


        questionHolder.setTag(R.id.recycler_lian_item_question_options_holder, item)


    }

}