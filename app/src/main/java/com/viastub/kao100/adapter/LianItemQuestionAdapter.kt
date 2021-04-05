package com.viastub.kao100.adapter

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.MyQuestionAction
import com.viastub.kao100.db.PracticeQuestion
import com.viastub.kao100.db.PracticeTemplate
import com.viastub.kao100.utils.Constants


/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
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
        holder.setText(R.id.lian_item_question_answer_reviewed, "答案: " + item.answerStandard)
        holder.setText(R.id.lian_item_question_answer_explained, "解析: " + item.answerKeyPoints)

        var questionMainText = holder.getView<TextView>(R.id.lian_item_question_main_text)
        questionMainText.visibility = if (item.text == null) View.GONE else View.VISIBLE

        var questionOptionsHolder =
            holder.getView<RecyclerView>(R.id.recycler_lian_item_question_options_holder)

        item.optionsDb?.let {
            if (item.type == Constants.practice_question_type_fill) {
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
            } else if (item.type == Constants.practice_question_type_select) {
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
            }
        }

        var answerReviewed = holder.getView<TextView>(R.id.lian_item_question_answer_reviewed)
        var answerExplained = holder.getView<TextView>(R.id.lian_item_question_answer_explained)

        answerReviewed.visibility =
            if (lianItem.submitted && item.answerStandard != null) View.VISIBLE else View.GONE
        answerExplained.visibility =
            if (lianItem.submitted && item.answerKeyPoints != null) View.VISIBLE else View.GONE


        questionOptionsHolder.setTag(R.id.recycler_lian_item_question_options_holder, item)


        //Functions zone
        var buttonFavorite = holder.getView<Button>(R.id.question_functions_favorite_btn)
        var buttonNote = holder.getView<Button>(R.id.question_functions_takenote_btn)
//        var buttonReport = holder.getView<Button>(R.id.question_functions_report_btn)
        var inputBoxNotes = holder.getView<EditText>(R.id.question_functions_notes_inputbox)

        item.myQuestionActionDb?.let {
            if (it.isFavorite == true) {
                buttonFavorite.setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions_orange)
            } else {
                buttonFavorite.setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions)
            }
            if (it.note.isNullOrBlank()) {
                buttonNote.setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions)
            } else {
                inputBoxNotes.setText(it.note!!.toCharArray(), 0, it.note!!.length)
                buttonNote.setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions_orange)
            }
        }

        questionEventListener?.let { actionListener ->
            buttonFavorite.setOnClickListener {
                actionListener.favoriteButtonClicked(it, item.myQuestionActionDb)
            }
            buttonNote.setOnClickListener {
                actionListener.noteButtonClicked(it, inputBoxNotes, item.myQuestionActionDb)
            }
        }


    }
}

interface QuestionActionListener {
    fun favoriteButtonClicked(v: View, myAction: MyQuestionAction?)
    fun noteButtonClicked(v: View, input: EditText, myAction: MyQuestionAction?)
}