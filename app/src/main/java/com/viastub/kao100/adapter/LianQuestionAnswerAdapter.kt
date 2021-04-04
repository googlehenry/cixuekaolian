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
import com.viastub.kao100.db.PracticeQuestionTemplate

class LianQuestionAnswerAdapter(
    var question: PracticeQuestion,
    var optionsHolder: RecyclerView,
    var lianItem: PracticeQuestionTemplate,
    var questionsHolder: RecyclerView
) :
    BaseQuickAdapter<PracticeAnswerOption, BaseViewHolder>(R.layout.fragment_lian_item_queston_option_text),
    LoadMoreModule {


    override fun convert(holder: BaseViewHolder, item: PracticeAnswerOption) {
        var indicator = holder.getView<ImageView>(R.id.lian_item_result_icon)
        var answerOption = holder.getView<EditText>(R.id.lian_item_answer_main)

        var itemOption = holder.getView<TextView>(R.id.lian_item_option_main)
        itemOption.visibility = View.GONE

        if (lianItem.submitted) {
            //current part answer check
            question.usersAnswers?.get(item.id)?.let {
                var correct: Boolean = item.correctAnswers?.let { ans -> ans.contains(it) } ?: false
                answerOption.setText(it.toCharArray(), 0, it.length)
                if (correct) {
                    indicator.visibility = View.VISIBLE
                    indicator.setBackgroundResource(R.drawable.icon_lian_result_tick)
                    answerOption.setTextColor(Color.parseColor("#2ea5ef"))
                } else {
                    indicator.visibility = View.VISIBLE
                    indicator.setBackgroundResource(R.drawable.icon_lian_result_cross)
                    answerOption.setTextColor(Color.parseColor("#ff0000"))
                }
            }
            if (question.usersAnswers?.get(item.id) == null && item.displayText != null) {
                answerOption.setText(
                    item.displayText?.toCharArray(),
                    0,
                    item.displayText!!.length
                )
            }
        } else {
            indicator.visibility = View.GONE
            answerOption.setTextColor(Color.parseColor("#333333"))

            question.usersAnswers?.get(item.id)?.let {
                answerOption.setText(it.toCharArray(), 0, it.length)
            }

            if (question.usersAnswers?.get(item.id) == null && item.displayText != null) {
                answerOption.setText(
                    item.displayText?.toCharArray(),
                    0,
                    item.displayText!!.length
                )
            }
        }


        var answerInput = holder.getView<EditText>(R.id.lian_item_answer_main)
        answerInput.setTag(R.id.lian_item_answer_main, item)
        answerInput.addTextChangedListener {
            var answerMap: MutableMap<Int, String> =
                question.usersAnswers ?: mutableMapOf<Int, String>()
            answerMap[item.id] = answerInput.text.toString().trim()
            question.usersAnswers = answerMap
            lianItem.submitted = false

        }

        item.layoutUIObject = answerInput//clear focus when select other places

    }

}