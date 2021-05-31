package com.viastub.kao100.adapter

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.*
import com.viastub.kao100.wigets.TextViewSelectionCallback


class LianItemQuestionAdapter(
    var lianItem: PracticeTemplate,
    var questionsHolder: RecyclerView,
    var questionEventListener: QuestionActionListener? = null
) :
    BaseQuickAdapter<PracticeQuestion, BaseViewHolder>(R.layout.fragment_lian_item_queston_selection_single),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: PracticeQuestion) {
        holder.setText(R.id.lian_item_question_main_seq, item.displaySeq.toString())
        holder.setText(R.id.lian_item_question_main_text, item.text)
        holder.setText(R.id.lian_item_question_answer_reviewed, "答案: " + item.answerStandardX())
        holder.setText(R.id.lian_item_question_answer_explained, "解析: " + item.answerKeyPoints)

        var questionMainText = holder.getView<TextView>(R.id.lian_item_question_main_text)
        questionMainText.visibility = if (item.text == null) View.GONE else View.VISIBLE
        questionMainText.customSelectionActionModeCallback =
            TextViewSelectionCallback(context, questionMainText, "练习,问题")

        var questionOptionsHolder =
            holder.getView<RecyclerView>(R.id.recycler_lian_item_question_options_holder)

        var questionFunctions = holder.getView<LinearLayout>(R.id.question_functions)

        item.optionsDb?.let {
            if (item.type == QuestionType.FILL.name) {
                var adapter =
                    LianQuestionAnswerAdapter(
                        item,
                        questionOptionsHolder,
                        lianItem,
                        questionsHolder
                    )
                adapter.data = it
                questionOptionsHolder.adapter = adapter
                questionOptionsHolder.layoutManager = GridLayoutManager(
                    context,
                    item.layoutOptionsPerRow
                )
            } else if (item.type == QuestionType.SELECT.name) {
                var adapter =
                    LianQuestionOptionAdapter(
                        item,
                        questionOptionsHolder,
                        lianItem,
                        questionsHolder
                    )
                adapter.data = it
                questionOptionsHolder.adapter = adapter
                questionOptionsHolder.layoutManager = GridLayoutManager(
                    context,
                    item.layoutOptionsPerRow
                )
            } else if (item.type in listOf(
                    QuestionType.CORRECT.name,
                    QuestionType.CORRECT_SINGLE.name
                )
            ) {
                var adapter =
                    LianQuestionCorrectionAdapter(
                        item,
                        questionOptionsHolder,
                        lianItem,
                        questionsHolder
                    )
                adapter.data = it
                questionOptionsHolder.adapter = adapter
                questionOptionsHolder.layoutManager = GridLayoutManager(
                    context,
                    item.layoutOptionsPerRow
                )
            }
        }

        var answerReviewed = holder.getView<TextView>(R.id.lian_item_question_answer_reviewed)
        var answerExplained = holder.getView<TextView>(R.id.lian_item_question_answer_explained)

        answerReviewed.visibility =
            if (lianItem.submitted && item.answerStandardX() != null) View.VISIBLE else View.GONE
        answerExplained.visibility =
            if (lianItem.submitted && item.answerKeyPoints != null) View.VISIBLE else View.GONE

        answerReviewed.customSelectionActionModeCallback =
            TextViewSelectionCallback(context, answerReviewed, "练习,答案")
        answerExplained.customSelectionActionModeCallback =
            TextViewSelectionCallback(context, answerExplained, "练习,解析")

        questionOptionsHolder.setTag(R.id.recycler_lian_item_question_options_holder, item)


        //Functions zone
        var buttonFavorite = holder.getView<ImageView>(R.id.question_functions_favorite_btn)
        var buttonNote = holder.getView<Button>(R.id.question_functions_takenote_btn)
        var buttonError = holder.getView<ImageView>(R.id.question_functions_errorbook_btn)
        var inputBoxNotes = holder.getView<EditText>(R.id.question_functions_notes_inputbox)

        item.myQuestionActionDb?.let {
            if (it.isFavorite == true) {
                buttonFavorite.setImageResource(R.drawable.ci_word_heart_selected)
            } else {
                buttonFavorite.setImageResource(R.drawable.ci_word_heart_gray)
            }
            if (it.note.isNullOrBlank()) {
                buttonNote.setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions_blue)
                buttonNote.text = "添加笔记"
            } else {
                inputBoxNotes.setText(it.note!!.toCharArray(), 0, it.note!!.length)
                buttonNote.setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions_orange)
                buttonNote.text = "我的笔记"
            }
        }

        buttonError.visibility = View.GONE
        item.myQuestionAnsweredHistoryDb?.let {
            if (it.wrongAttemptNo > 0) {
                buttonError.visibility = View.VISIBLE
            }
        }

        questionEventListener?.let { actionListener ->
            buttonFavorite.setOnClickListener {
                actionListener.favoriteButtonClicked(it, item.myQuestionActionDb)
            }
            buttonNote.setOnClickListener {
                actionListener.noteButtonClicked(it, inputBoxNotes, item.myQuestionActionDb)
            }
            buttonError.setOnClickListener {
                actionListener.errorButtonClicked(it, item.myQuestionAnsweredHistoryDb)
            }
        }


    }
}

interface QuestionActionListener {
    fun favoriteButtonClicked(v: View, myAction: MyQuestionAction?)
    fun noteButtonClicked(v: View, input: EditText, myAction: MyQuestionAction?)
    fun errorButtonClicked(it: View?, myQuestionAnsweredHistoryDb: MyQuestionAnsweredHistory?)
}