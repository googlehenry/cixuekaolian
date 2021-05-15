package com.viastub.kao100.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.PracticeAnswerOption
import com.viastub.kao100.db.PracticeQuestion
import com.viastub.kao100.db.PracticeTemplate

class LianQuestionOptionAdapter(
    var question: PracticeQuestion,
    var optionsHolder: RecyclerView,
    var lianItem: PracticeTemplate,
    var questionsHolder: RecyclerView
) : BaseQuickAdapter<PracticeAnswerOption, BaseViewHolder>(R.layout.fragment_lian_item_queston_option_text),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: PracticeAnswerOption) {
        var indicator = holder.getView<ImageView>(R.id.lian_item_result_icon)
        var itemOption = holder.getView<TextView>(R.id.lian_item_option_main)
        itemOption.text = item.displayText
//        itemOption.customSelectionActionModeCallback =
//            TextViewSelectionCallback(context, itemOption)

        if (!lianItem.submitted) {
            indicator.visibility = View.GONE
            itemOption.isEnabled = false
            question.usersAnswers?.let {
                if (it.contains(item.id!!)) {
                    itemOption.setTextColor(Color.parseColor("#2ea5ef"))
                } else {
                    itemOption.setTextColor(Color.parseColor("#000000"))
                }
            }
        } else {
            question.usersAnswers?.let {
                if (it.contains(item.id!!)) {
                    if (!item.correctAnswers().isNullOrEmpty()) {
                        itemOption.setTextColor(Color.parseColor("#2ea5ef"))
                        indicator.setBackgroundResource(R.drawable.icon_lian_result_tick)
                        indicator.visibility = View.VISIBLE
                        question.userAnswersChecks[item.id!!] = true
                    } else {
                        itemOption.setTextColor(Color.parseColor("#FF0000"))
                        indicator.setBackgroundResource(R.drawable.icon_lian_result_cross)
                        indicator.visibility = View.VISIBLE
                        question.userAnswersChecks[item.id!!] = false
                    }
                } else if (!it.isNullOrEmpty()) {
                    if (!item.correctAnswers().isNullOrEmpty()) {
                        itemOption.setTextColor(Color.parseColor("#2ea5ef"))
                        indicator.setBackgroundResource(R.drawable.icon_lian_result_tick)
                        indicator.visibility = View.VISIBLE
                    } else {
                        indicator.visibility = View.GONE
                        itemOption.setTextColor(Color.parseColor("#333333"))
                    }
                } else {
                    indicator.visibility = View.GONE
                    itemOption.setTextColor(Color.parseColor("#333333"))
                }
            }
        }

        var itemAnswer = holder.getView<TextView>(R.id.lian_item_answer_main)
        itemAnswer.visibility = View.GONE


        var bookItemHolder = holder.getView<LinearLayout>(R.id.lian_item_option_holder)
        bookItemHolder.setTag(R.id.lian_item_option_holder, item)
        if (!lianItem.submitted) {
            bookItemHolder.setOnClickListener {
                var answerMap: MutableMap<Int, String> =
                    question.usersAnswers ?: mutableMapOf<Int, String>()

                if (question.requireAnsweredOptionsNo <= 1) {
                    answerMap = mutableMapOf<Int, String>()
                    answerMap[item.id!!] = "selected"
                } else {
                    answerMap[item.id!!] = "selected"
                }

                question.usersAnswers = answerMap
                lianItem.submitted = false

                optionsHolder.adapter?.notifyDataSetChanged()
            }
        }
    }

}