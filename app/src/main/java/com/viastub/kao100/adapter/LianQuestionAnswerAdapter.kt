package com.viastub.kao100.adapter

import android.graphics.Color
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.PracticeAnswerOption
import com.viastub.kao100.db.PracticeQuestion
import com.viastub.kao100.db.PracticeTemplate

class LianQuestionAnswerAdapter(
    var question: PracticeQuestion,
    var optionsHolder: RecyclerView,
    var lianItem: PracticeTemplate,
    var questionsHolder: RecyclerView
) :
    BaseQuickAdapter<PracticeAnswerOption, BaseViewHolder>(R.layout.fragment_lian_item_queston_option_text),
    LoadMoreModule {


    override fun convert(holder: BaseViewHolder, item: PracticeAnswerOption) {
        var indicator = holder.getView<ImageView>(R.id.lian_item_result_icon)
        var answerInput = holder.getView<EditText>(R.id.lian_item_answer_main)
        var itemOption = holder.getView<TextView>(R.id.lian_item_option_main)
        itemOption.visibility = View.GONE


        if (lianItem.submitted) {
            //current part answer check
            answerInput.isEnabled = false
            question.usersAnswers?.get(item.id)?.let {
                var correct: Boolean =
                    item.correctAnswersSplitByPipes?.let { ans -> (ans == it) } ?: false
                answerInput.setText(it.toCharArray(), 0, it.length)
                if (correct) {
                    indicator.visibility = View.VISIBLE
                    indicator.setBackgroundResource(R.drawable.icon_lian_result_tick)
                    answerInput.setTextColor(Color.parseColor("#2ea5ef"))
                    question.userAnswersChecks[item.id!!] = true
                } else {
                    indicator.visibility = View.VISIBLE
                    indicator.setBackgroundResource(R.drawable.icon_lian_result_cross)
                    answerInput.setTextColor(Color.parseColor("#ff0000"))
                    question.userAnswersChecks[item.id!!] = false
                }
            }
            if (question.usersAnswers?.get(item.id) == null && item.displayText != null) {
                answerInput.setText(
                    item.displayText?.toCharArray(),
                    0,
                    item.displayText!!.length
                )
            } else if (question.usersAnswers?.get(item.id) != null) {
                answerInput.setText(
                    question.usersAnswers?.get(item.id)?.toCharArray(),
                    0,
                    question.usersAnswers?.get(item.id)!!.length
                )
            }
        } else {
            indicator.visibility = View.GONE
            answerInput.setTextColor(Color.parseColor("#333333"))

            question.usersAnswers?.get(item.id)?.let {
                answerInput.setText(it.toCharArray(), 0, it.length)
            }

            if (question.usersAnswers?.get(item.id) == null && item.displayText != null) {
                answerInput.setText(
                    item.displayText?.toCharArray(),
                    0,
                    item.displayText!!.length
                )
            } else if (question.usersAnswers?.get(item.id) != null) {
                answerInput.setText(
                    question.usersAnswers?.get(item.id)?.toCharArray(),
                    0,
                    question.usersAnswers?.get(item.id)!!.length
                )
            }
        }



        answerInput.setTag(R.id.lian_item_answer_main, item)
        answerInput.addTextChangedListener {
            question.usersAnswers[item.id!!] = answerInput.text.toString().trim()
            lianItem.submitted = false
        }

        item.layoutUIObject = answerInput//clear focus when select other places

    }

}