package com.viastub.kao100.module.lian

import android.graphics.Color
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.viastub.kao100.R
import com.viastub.kao100.adapter.LianItemQuestionAdapter
import com.viastub.kao100.adapter.QuestionActionListener
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.*
import com.viastub.kao100.utils.Variables
import kotlinx.android.synthetic.main.activity_lian_item_page.*
import java.io.File


class LianPage0ActivityClone : BaseActivity(), QuestionActionListener {

    override fun id(): Int {
        return R.layout.activity_lian_item_page
    }


    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        var sections = intent?.extras?.get("sections") as ArrayList<PracticeSection>

        Variables.availableTemplateIds =
            sections?.flatMap { it.practiceTemplates() ?: mutableListOf() }.toMutableList()
        Variables.currentTemplateIdIdx =
            if (Variables.availableTemplateIds!!.size > 0) 0 else -1

        if (Variables.currentTemplateIdIdx >= 0) {
            //load question from db 1 at a time
            loadCurrentQuestionTemplate()
        }

    }

    private fun loadCurrentQuestionTemplate() {
        awaitAsync(dataAction = {
            RoomDB.get(applicationContext).practiceTemplate()
                .getById(Variables.availableTemplateIds!![Variables.currentTemplateIdIdx])
        }, uiAction = {
            updateUI(it)
        })
    }

    private fun updateUI(template: PracticeTemplate) {
        lian_item_result_submit.isEnabled = true
        lian_item_result_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_blue)

        lian_item_seq.text =
            "${Variables.currentTemplateIdIdx + 1}/${Variables.availableTemplateIds.size}"
        lian_item_category.text = template.category
        lian_item_requirment.text = "要求:" + template.requirement
        lian_item_main_text.text = template.itemMainText

        lian_item_main_text.visibility =
            if (template.itemMainAudioPath != null) View.GONE else View.VISIBLE
        lian_item_main_audio_start.visibility =
            if (template.itemMainAudioPath != null) View.VISIBLE else View.GONE
        lian_item_main_holder.visibility =
            if (template.itemMainAudioPath == null && template.itemMainText == null) View.GONE else View.VISIBLE
        lian_item_switch_next_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)

        lian_item_switch_prev_btn.setOnClickListener {
            if (template.submitted) {
                turnTo(template, -1)
            } else {
                Toast.makeText(this, "请先完成当前题目.", Toast.LENGTH_SHORT).show()
            }
            loseFocusForEditable(template)
        }

        lian_item_switch_next_btn.setOnClickListener {
            if (template.submitted) {
                turnTo(template, 1)
            } else {
                Toast.makeText(this, "请先完成当前题目.", Toast.LENGTH_SHORT).show()
            }
            loseFocusForEditable(template)
        }
        lian_item_explanations.setOnClickListener {
            loseFocusForEditable(template)
        }

        lian_item_explanations.visibility = View.GONE
        lian_item_explanations.text = template.keyPoints

        lian_item_main_audio_start.setOnClickListener {
            template.itemMainAudioPath?.let { plyDemoMp3Reading(it) }
        }

        lian_item_result_submit.setOnClickListener {
            template.submitted = true
            recycler_view_lian_item_questions.adapter?.notifyDataSetChanged()
            showExplanationForTemplate(template)
            loseFocusForEditable(template)

            lian_item_result_submit.postDelayed({
                checkUserAnsweredResult(template)
            }, 100)

            lian_item_result_submit.isEnabled = false
            lian_item_result_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
        }


        template.practiceQuestions()?.let {

            awaitAsync(
                dataAction = {
                    RoomDB.get(applicationContext).practiceQuestion().getByIds(it)
                        .mapIndexed { qidx, qs ->
                            var options = RoomDB.get(applicationContext).practiceAnswerOption()
                                .getByIds(qs.optionPractices()!!)
                                .mapIndexed { index, practiceAnswerOption ->
                                    practiceAnswerOption.displaySeq = index + 1
                                    practiceAnswerOption
                                }
                                .toMutableList()
                            var myAction = RoomDB.get(applicationContext).myQuestionAction()
                                .getByQuestionIdsOfUser(Variables.currentUserId, qs.id)

                            if (myAction == null) {
                                RoomDB.get(applicationContext).myQuestionAction()
                                    .insert(MyQuestionAction(Variables.currentUserId, qs.id))

                                myAction = RoomDB.get(applicationContext).myQuestionAction()
                                    .getByQuestionIdsOfUser(Variables.currentUserId, qs.id)
                            }

                            qs.optionsDb = options
                            qs.myQuestionActionDb = myAction
                            qs.displaySeq = qidx + 1


                            qs
                        }.toMutableList()
                },
                uiAction = {
                    template.questionsDb = it

                    recycler_view_lian_item_questions.layoutManager =
                        GridLayoutManager(this, template.layoutQuestionsPerRow)
                    var adapter =
                        LianItemQuestionAdapter(template, recycler_view_lian_item_questions, this)
                    adapter.data = it
                    recycler_view_lian_item_questions.adapter = adapter

                })

        }
        template.itemMainAudioPath?.let {
            mediaPlayer = MediaPlayer.create(this, File(it).toUri())
        }


        template.countDownTimer =
            setUpTimer((template.totalTimeInMinutes * 60 * 1000).toLong())
        template.countDownTimer?.start()

    }

    private fun checkUserAnsweredResult(template: PracticeTemplate) {
        var answeredHistoryForThisTemplate = template.questionsDb?.map { pq ->
            var resultSet: MutableSet<Boolean?> = mutableSetOf()
            resultSet.addAll(pq.userAnswersChecks.values)

            var result = when (resultSet.size) {
                1 -> resultSet.first()
                else -> null
            }

            pq.userAnswersChecks = mutableMapOf()

            MyQuestionAnsweredHistory(
                Variables.currentUserId,
                pq.id,
                answerIsCorrect = result,
                optionalPracticeTemplateId = template.id
            ).setMyAnswersJson(pq.usersAnswers)
        }?.toMutableList()

        answeredHistoryForThisTemplate?.let {
            launchAsync {
                RoomDB.get(applicationContext).myQuestionAnsweredHistory().insertAll(it)
            }
        }

        var summayrChecked =
            answeredHistoryForThisTemplate?.map { it.answerIsCorrect }?.groupBy { it }

        var right = summayrChecked?.get(true)?.size ?: 0
        var wrong = summayrChecked?.get(false)?.size ?: 0
        var missing = summayrChecked?.get(null)?.size ?: 0
        var rate = (right.toDouble() / (right + wrong + missing).toDouble() * 100).toInt()

        lian_item_category.text = template.category + "(正确率:$rate%)"
        lian_item_scores.text = "对:$right 错:$wrong 未答:$missing 正确率:$rate%"
        lian_item_scores.visibility = View.VISIBLE

    }

    private fun turnTo(oldTemplate: PracticeTemplate, step: Int) {
        //Load next template
        if (step != 0) {
            var toIndex = Variables.currentTemplateIdIdx + step
            when {
                toIndex >= Variables.availableTemplateIds.size -> {
                    Variables.currentTemplateIdIdx =
                        Variables.availableTemplateIds.size - 1
                    Toast.makeText(this, "已到最后一题", Toast.LENGTH_SHORT).show()
                }
                toIndex < 0 -> {
                    Variables.currentTemplateIdIdx = 0
                    Toast.makeText(this, "已到第一题", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //Stop the timer first for previous question
                    oldTemplate.countDownTimer?.cancel()
                    stopPlayer()
                    Variables.currentTemplateIdIdx = toIndex
                    loadCurrentQuestionTemplate()
                }
            }

        }
    }

    private fun loseFocusForEditable(template: PracticeTemplate) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        template.questionsDb?.forEach {
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

    private fun showExplanationForTemplate(template: PracticeTemplate) {
        if (template.submitted) {
            lian_item_explanations.visibility = View.VISIBLE
            lian_item_main_text.visibility = View.VISIBLE
            lian_item_main_holder.visibility =
                if (template.itemMainAudioPath == null && template.itemMainText == null) View.GONE else View.VISIBLE
            lian_item_switch_next_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
        } else {
            lian_item_explanations.visibility = View.GONE
            lian_item_main_text.visibility = View.INVISIBLE
            lian_item_main_holder.visibility = View.GONE
            lian_item_switch_next_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
        }

    }

    var mediaPlayer: MediaPlayer? = null

    private fun plyDemoMp3Reading(audioFile: String) {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        stopPlayer()
    }

    private fun stopPlayer() {
        mediaPlayer?.stop()
        mediaPlayer = null
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

    override fun favoriteButtonClicked(v: View, myAction: MyQuestionAction?) {
        myAction?.let {
            it.isFavorite = (it.isFavorite != true)

            if (it.isFavorite == true) {
                v.setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions_orange)
            } else {
                v.setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions)
            }

            launchAsync { RoomDB.get(applicationContext).myQuestionAction().insert(it) }
        }
    }

    override fun noteButtonClicked(v: View, input: EditText, myAction: MyQuestionAction?) {
        myAction?.let {
            if (input.isVisible) {
                val entered = input.text.toString().trim()
                var needSave = false
                if (it.note != entered) {
                    it.note = entered
                    needSave = true
                } else if (it.note != null && entered.isNullOrBlank()) {
                    it.note = null
                    needSave = true
                }
                if (needSave) {
                    launchAsync { RoomDB.get(applicationContext).myQuestionAction().insert(it) }
                }

                input.visibility = View.GONE
                (v as Button).text = "笔记"

                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    input.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                if (input.isFocused) {
                    input.clearFocus()
                }
            } else {
                input.visibility = View.VISIBLE
                (v as Button).text = "保存"
            }

        }
    }
}