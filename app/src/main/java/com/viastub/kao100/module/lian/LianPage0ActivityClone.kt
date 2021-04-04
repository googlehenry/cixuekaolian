package com.viastub.kao100.module.lian

import android.media.MediaPlayer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.viastub.kao100.R
import com.viastub.kao100.adapter.LianItemQuestionAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.PracticeQuestionTemplate
import com.viastub.kao100.db.PracticeSection
import com.viastub.kao100.db.RoomDB
import kotlinx.android.synthetic.main.activity_lian_item_page.*


class LianPage0ActivityClone : BaseActivity() {
    companion object {
        var currentQuestionTemplateIdIdx: Int = -1
        lateinit var availableQuestionTemplateIds: MutableList<Int>
    }

    override fun id(): Int {
        return R.layout.activity_lian_item_page
    }


    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        var sections = intent?.extras?.get("sections") as ArrayList<PracticeSection>

        availableQuestionTemplateIds =
            sections?.flatMap { it.practiceQuestionTemplates() ?: mutableListOf() }.toMutableList()
        currentQuestionTemplateIdIdx = if (availableQuestionTemplateIds!!.size > 0) 0 else -1

        if (currentQuestionTemplateIdIdx >= 0) {
            //load question from db 1 at a time
            loadCurrentQuestionTemplate()
        }

    }

    private fun loadCurrentQuestionTemplate() {
        doAsync(dataAction = {
            RoomDB.get(applicationContext).practiceQuestionTemplate()
                .getById(availableQuestionTemplateIds!![currentQuestionTemplateIdIdx])
        }, uiAction = {
            updateUI(it)
        })
    }

    private fun updateUI(questionTemplate: PracticeQuestionTemplate) {


        lian_item_category.text = questionTemplate.category
        lian_item_requirment.text = questionTemplate.requirement
        lian_item_main_text.text = questionTemplate.itemMainText

        lian_item_main_text.visibility =
            if (questionTemplate.itemMainAudio != null) View.GONE else View.VISIBLE
        lian_item_main_audio_start.visibility =
            if (questionTemplate.itemMainAudio != null) View.VISIBLE else View.GONE
        lian_item_main_holder.visibility =
            if (questionTemplate.itemMainAudio == null && questionTemplate.itemMainText == null) View.GONE else View.VISIBLE


        lian_item_switch_next_btn.setOnClickListener {
            if (questionTemplate.submitted) {
                //Load next template
                currentQuestionTemplateIdIdx += 1
                if (currentQuestionTemplateIdIdx < availableQuestionTemplateIds.size) {
                    loadCurrentQuestionTemplate()
                } else {
                    Toast.makeText(this, "没有更多了", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "请先完成当前题目.", Toast.LENGTH_SHORT).show()
            }

            loseFocusForEditable(questionTemplate)
        }
        lian_item_explanations.setOnClickListener {
            loseFocusForEditable(questionTemplate)
        }

        lian_item_explanations.visibility = View.INVISIBLE
        lian_item_explanations.text = questionTemplate.keyPoints

        lian_item_main_audio_start.setOnClickListener { plyDemoMp3Reading() }

        lian_item_result_submit.setOnClickListener {
            questionTemplate.submitted = true
            recycler_view_lian_item_questions.adapter?.notifyDataSetChanged()
            showExplanationForTemplate(questionTemplate)
            loseFocusForEditable(questionTemplate)
        }


        questionTemplate.practiceQuestions()?.let {

            doAsync(dataAction = {
                RoomDB.get(applicationContext).practiceQuestion().getByIds(it).map { qs ->
                    var options = RoomDB.get(applicationContext).practiceAnswerOption()
                        .getByIds(qs.optionPractices()!!).toMutableList()

                    qs.optionsDb = options
                    qs
                }.toMutableList()
            },
                uiAction = {
                    questionTemplate.questionsDb = it

                    recycler_view_lian_item_questions.layoutManager =
                        GridLayoutManager(this, questionTemplate.layoutQuestionsPerRow)
                    var adapter =
                        LianItemQuestionAdapter(questionTemplate, recycler_view_lian_item_questions)
                    adapter.data = it
                    recycler_view_lian_item_questions.adapter = adapter

                })

        }

    }

    private fun loseFocusForEditable(questionTemplate: PracticeQuestionTemplate) {
        questionTemplate.questionsDb?.forEach {
            it.optionsDb?.forEach {
                it.layoutUIObject?.let {
                    if (it is EditText) {
                        if (it.isFocused) {
                            it.clearFocus()
                            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(
                                it.windowToken,
                                InputMethodManager.HIDE_NOT_ALWAYS
                            )
                        }
                    }
                }
            }
        }
    }

    private fun showExplanationForTemplate(questionTemplate: PracticeQuestionTemplate) {
        if (questionTemplate.submitted) {
            lian_item_explanations.visibility = View.VISIBLE
            lian_item_main_text.visibility = View.VISIBLE
            lian_item_main_holder.visibility =
                if (questionTemplate.itemMainAudio == null && questionTemplate.itemMainText == null) View.GONE else View.VISIBLE
            lian_item_switch_next_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
        } else {
            lian_item_explanations.visibility = View.INVISIBLE
            lian_item_main_text.visibility = View.INVISIBLE
            lian_item_main_holder.visibility = View.GONE
            lian_item_switch_next_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
        }

    }

    var mediaPlayer: MediaPlayer? = null

    private fun plyDemoMp3Reading() {
        mediaPlayer =
            mediaPlayer ?: MediaPlayer.create(this, R.raw.demo_2018_national_test_vol2_section1)
        mediaPlayer?.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer?.stop()
    }

}