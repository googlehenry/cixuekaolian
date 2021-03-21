package com.qianli.cixuekaolian.adapter

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
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.LianItem
import com.qianli.cixuekaolian.beans.LianItemQuestion
import com.qianli.cixuekaolian.beans.LianQuestionAnswer

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class LianQuestionAnswerAdapter(
    var question: LianItemQuestion,
    var optionsHolder: RecyclerView,
    var lianItem: LianItem,
    var questionsHolder: RecyclerView
) :
    BaseQuickAdapter<LianQuestionAnswer, BaseViewHolder>(R.layout.fragment_lian_item_queston_option_text),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: LianQuestionAnswer) {
        var indicator = holder.getView<ImageView>(R.id.lian_item_result_icon)
        var answerOption = holder.getView<EditText>(R.id.lian_item_answer_main)

        var itemOption = holder.getView<TextView>(R.id.lian_item_option_main)
        itemOption.visibility = View.GONE

        if (lianItem.submitted) {
            question.userAnsweredReplies?.let {
                var correct: Boolean =
                    it.any { anser -> item.correctAnswers?.let { it.contains(anser) } ?: false }
                answerOption.setText(it.first().toCharArray(), 0, it.first().length)
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
        } else {
            indicator.visibility = View.GONE
            answerOption.setTextColor(Color.parseColor("#333333"))
            question.userAnsweredReplies?.let {
                answerOption.setText(it.first().toCharArray(), 0, it.first().length)
            }
        }


        var answerInput = holder.getView<EditText>(R.id.lian_item_answer_main)
        answerInput.setTag(R.id.lian_item_answer_main, item)
        answerInput.addTextChangedListener {
            question.userAnsweredReplies = mutableSetOf(answerInput.text.toString().trim())
            lianItem.submitted = false
        }
    }

}