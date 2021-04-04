package com.viastub.kao100.module.lian

import android.graphics.Color
import android.media.MediaPlayer
import android.os.CountDownTimer
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
import com.viastub.kao100.utils.Variables
import kotlinx.android.synthetic.main.activity_lian_item_page.*


class LianPage0ActivityClone : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_lian_item_page
    }


    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        var sections = intent?.extras?.get("sections") as ArrayList<PracticeSection>

        Variables.availableQuestionTemplateIds =
            sections?.flatMap { it.practiceQuestionTemplates() ?: mutableListOf() }.toMutableList()
        Variables.currentQuestionTemplateIdIdx =
            if (Variables.availableQuestionTemplateIds!!.size > 0) 0 else -1

        if (Variables.currentQuestionTemplateIdIdx >= 0) {
            //load question from db 1 at a time
            loadCurrentQuestionTemplate()
        }

    }

    private fun loadCurrentQuestionTemplate() {
        doAsync(dataAction = {
            RoomDB.get(applicationContext).practiceQuestionTemplate()
                .getById(Variables.availableQuestionTemplateIds!![Variables.currentQuestionTemplateIdIdx])
        }, uiAction = {
            updateUI(it)
        })
    }

    private fun updateUI(questionTemplate: PracticeQuestionTemplate) {

        lian_item_seq.text =
            "${Variables.currentQuestionTemplateIdIdx + 1}/${Variables.availableQuestionTemplateIds.size}"
        lian_item_category.text = questionTemplate.category
        lian_item_requirment.text = "要求:" + questionTemplate.requirement
        lian_item_main_text.text = questionTemplate.itemMainText

        lian_item_main_text.visibility =
            if (questionTemplate.itemMainAudio != null) View.GONE else View.VISIBLE
        lian_item_main_audio_start.visibility =
            if (questionTemplate.itemMainAudio != null) View.VISIBLE else View.GONE
        lian_item_main_holder.visibility =
            if (questionTemplate.itemMainAudio == null && questionTemplate.itemMainText == null) View.GONE else View.VISIBLE

        lian_item_switch_prev_btn.setOnClickListener {
            if (questionTemplate.submitted) {
                turnTo(questionTemplate, -1)
            } else {
                Toast.makeText(this, "请先完成当前题目.", Toast.LENGTH_SHORT).show()
            }
            loseFocusForEditable(questionTemplate)
        }

        lian_item_switch_next_btn.setOnClickListener {
            if (questionTemplate.submitted) {
                turnTo(questionTemplate, 1)
            } else {
                Toast.makeText(this, "请先完成当前题目.", Toast.LENGTH_SHORT).show()
            }
            loseFocusForEditable(questionTemplate)
        }
        lian_item_explanations.setOnClickListener {
            loseFocusForEditable(questionTemplate)
        }

        lian_item_explanations.visibility = View.GONE
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
                RoomDB.get(applicationContext).practiceQuestion().getByIds(it)
                    .mapIndexed { qidx, qs ->
                        var options = RoomDB.get(applicationContext).practiceAnswerOption()
                            .getByIds(qs.optionPractices()!!)
                            .mapIndexed { index, practiceAnswerOption ->
                                practiceAnswerOption.displaySeq = index + 1
                                practiceAnswerOption
                            }
                            .toMutableList()

                        qs.optionsDb = options
                        qs.displaySeq = qidx + 1
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

        questionTemplate.countDownTimer =
            setUpTimer((questionTemplate.totalTimeInMinutes * 60 * 1000).toLong())
        questionTemplate.countDownTimer?.start()

    }

    private fun turnTo(oldQuestionTemplate: PracticeQuestionTemplate, step: Int) {
        //Load next template
        if (step != 0) {
            var toIndex = Variables.currentQuestionTemplateIdIdx + step
            when {
                toIndex >= Variables.availableQuestionTemplateIds.size -> {
                    Variables.currentQuestionTemplateIdIdx =
                        Variables.availableQuestionTemplateIds.size - 1
                    Toast.makeText(this, "已到最后一题", Toast.LENGTH_SHORT).show()
                }
                toIndex < 0 -> {
                    Variables.currentQuestionTemplateIdIdx = 0
                    Toast.makeText(this, "已到第一题", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //Stop the timer first for previous question
                    oldQuestionTemplate.countDownTimer?.cancel()

                    Variables.currentQuestionTemplateIdIdx = toIndex
                    loadCurrentQuestionTemplate()
                }
            }

        }
    }

    private fun loseFocusForEditable(questionTemplate: PracticeQuestionTemplate) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        questionTemplate.questionsDb?.forEach {
            it.optionsDb?.forEach {
                it.layoutUIObject?.let {
                    if (it is EditText) {
                        imm.hideSoftInputFromWindow(
                            it.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS
                        )

                        if (it.isFocused) {
                            it.clearFocus()
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
            lian_item_explanations.visibility = View.GONE
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

    fun setUpTimer(milliSeconds: Long): CountDownTimer {
        var countDownTimer = object : CountDownTimer(milliSeconds, 500) {
            override fun onTick(millisUntilFinished: Long) {
                var minutes = millisUntilFinished / 60000
                var seconds = (millisUntilFinished % 60000) / 1000
                practice_countdown_timer.text =
                    "[${if (minutes < 10) "0" + minutes else minutes}:${if (seconds < 10) "0" + seconds else seconds}]"
            }

            override fun onFinish() {
                practice_countdown_timer.setTextColor(Color.parseColor("#ff0000"))
                practice_countdown_timer.text = "[已超时]"
            }

        }
        return countDownTimer
    }
}