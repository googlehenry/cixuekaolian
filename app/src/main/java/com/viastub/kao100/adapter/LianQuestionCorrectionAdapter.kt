package com.viastub.kao100.adapter

import android.graphics.Color
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
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
import com.viastub.kao100.db.QuestionType

class LianQuestionCorrectionAdapter(
    var question: PracticeQuestion,
    var optionsHolder: RecyclerView,
    var template: PracticeTemplate,
    var questionsHolder: RecyclerView
) :
    BaseQuickAdapter<PracticeAnswerOption, BaseViewHolder>(R.layout.fragment_lian_item_queston_option_text),
    LoadMoreModule {


    override fun convert(holder: BaseViewHolder, item: PracticeAnswerOption) {
        var answerInput = holder.getView<EditText>(R.id.lian_item_answer_main)
        var itemOption = holder.getView<TextView>(R.id.lian_item_option_main)
        itemOption.visibility = View.GONE
        answerInput.visibility = View.GONE

        var indicator = holder.getView<ImageView>(R.id.lian_item_result_icon)
        var correctUiGroup = holder.getView<LinearLayout>(R.id.lian_item_correction_main)
        var correctUiFrom = holder.getView<EditText>(R.id.lian_item_correction_from)
        var correctUiTo = holder.getView<EditText>(R.id.lian_item_correction_to)
        correctUiGroup.visibility = View.VISIBLE


        if (template.submitted) {
            //current part answer check
            correctUiFrom.isEnabled = false
            correctUiTo.isEnabled = false

            var correct = false
            var correctMatchIfAnyForExamTypeCorrection: String? = null
            var wrongInputByUser: String? = null

            if (question.type == QuestionType.CORRECT_SINGLE.name) {
                question.usersAnswers[item.id]?.let {
                    item.correctAnswers()?.contains(it)?.let {
                        correct = it
                    }
                }
            } else {
                var pooledUserAnsers =
                    template.questionsDb?.flatMap { it.usersAnswers.values }?.toMutableSet()
                        ?: mutableSetOf()

                var userAnsweredCorrectOnes = pooledUserAnsers.filter {
                    item.correctAnswers()?.contains(
                        it
                    ) ?: false
                }
                correct = (!userAnsweredCorrectOnes.isNullOrEmpty())
                if (correct) {
                    correctMatchIfAnyForExamTypeCorrection = userAnsweredCorrectOnes[0]
                } else {
                    wrongInputByUser = pooledUserAnsers.find {
                        !template.pooledQuestionStandardAnswers().values.flatten().contains(it)
                    }
                }
            }


            if (correct) {
                indicator.visibility = View.VISIBLE
                indicator.setBackgroundResource(R.drawable.icon_lian_result_tick)
                correctUiFrom.setTextColor(Color.parseColor("#2ea5ef"))
                correctUiTo.setTextColor(Color.parseColor("#2ea5ef"))

                if (question.type == QuestionType.CORRECT_SINGLE.name) {
                    //keep what user answered
                    question.usersAnswers[item.id]?.let {
                        setCorrectItem(correctUiFrom, it, correctUiTo)
                    }
                } else {
                    //adjust user's answer
                    correctMatchIfAnyForExamTypeCorrection?.let {
                        setCorrectItem(correctUiFrom, it, correctUiTo)
                    }

                }
            } else {

                if (question.type == QuestionType.CORRECT_SINGLE.name) {
                    //keep what user answered
                    question.usersAnswers[item.id]?.let {
                        setWrongIndicator(indicator, correctUiFrom, correctUiTo)
                        setCorrectItem(correctUiFrom, it, correctUiTo)
                    }
                } else {
                    //assign wrong answer to user-once-filled boxes
                    (correctUiFrom as TextView?)?.setText("")
                    (correctUiTo as TextView?)?.setText("")
                    if (question.usersAnswers[item.id] != null) {
                        setWrongIndicator(indicator, correctUiFrom, correctUiTo)
                        wrongInputByUser?.let {
                            setCorrectItem(correctUiFrom, it, correctUiTo)

                            template.questionsDb?.forEach {
                                it.usersAnswers.filter { entry -> entry.value == wrongInputByUser }.keys.forEach { key ->
                                    it.usersAnswers.remove(key)
                                }
                            }

                        }
                    }
                }
            }
        } else {
            indicator.visibility = View.GONE
            correctUiFrom.setTextColor(Color.parseColor("#333333"))
            correctUiTo.setTextColor(Color.parseColor("#333333"))

            question.usersAnswers?.get(item.id)?.let {
                correctUiFrom.setText(it.split("->")[0].toCharArray(), 0, it.split("->")[0].length)
                correctUiTo.setText(it.split("->")[1].toCharArray(), 0, it.split("->")[1].length)
            }

        }


        correctUiGroup.setTag(R.id.lian_item_correction_main, item)

        correctUiFrom.addTextChangedListener {
            var fromText = correctUiFrom.text.toString().trim()
            var toText = correctUiTo.text.toString().trim()

            question.usersAnswers[item.id!!] = "$fromText->$toText"
        }

        correctUiTo.addTextChangedListener {
            var fromText = correctUiFrom.text.toString().trim()
            var toText = correctUiTo.text.toString().trim()

            question.usersAnswers[item.id!!] = "$fromText->$toText"
        }

    }

    private fun setWrongIndicator(
        indicator: ImageView,
        correctUiFrom: EditText,
        correctUiTo: EditText
    ) {
        indicator.visibility = View.VISIBLE
        indicator.setBackgroundResource(R.drawable.icon_lian_result_cross)
        correctUiFrom.setTextColor(Color.parseColor("#ff0000"))
        correctUiTo.setTextColor(Color.parseColor("#ff0000"))
    }

    private fun setCorrectItem(
        correctUiFrom: EditText,
        value: String,
        correctUiTo: EditText
    ) {
        correctUiFrom.setText(
            value.split("->")[0].toCharArray(),
            0,
            value.split("->")[0].length
        )
        correctUiTo.setText(
            value.split("->")[1].toCharArray(),
            0,
            value.split("->")[1].length
        )
    }

}