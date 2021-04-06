package com.viastub.kao100.module.lian

import android.app.AlertDialog
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
        val templateId = Variables.availableTemplateIds!![Variables.currentTemplateIdIdx]
        Variables.availableTemplatesMap[templateId]?.let {
            updateUI(it)
        } ?: awaitAsync(dataAction = {
            RoomDB.get(applicationContext).practiceTemplate()
                .getById(templateId)
        }, uiAction = {
            updateUI(it!!)
        })
    }

    private fun updateUI(template: PracticeTemplate) {

        lian_item_seq.text =
            "${Variables.currentTemplateIdIdx + 1}/${Variables.availableTemplateIds.size}"
        lian_item_category.text = template.category
        lian_item_requirment.text =
            "要求:${template.requirement} (本题${template.totalScore}分,共${template.practiceQuestions()?.size ?: 0}小题,每小题${template.totalScore / (template.practiceQuestions()?.size ?: 1)}分)"
        lian_item_main_text.text = template.itemMainText

        lian_item_main_text.visibility =
            if (template.itemMainAudioPath != null) View.GONE else View.VISIBLE
        lian_item_main_audio_start.visibility =
            if (template.itemMainAudioPath != null) View.VISIBLE else View.GONE
        lian_item_main_holder.visibility =
            if (template.itemMainAudioPath == null && template.itemMainText == null) View.GONE else View.VISIBLE
        if (Variables.currentTemplateIdIdx < Variables.availableTemplateIds.size - 1) {
            lian_item_switch_next_btn.isEnabled = true
            lian_item_switch_next_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
        } else {
            lian_item_switch_next_btn.isEnabled = false
            lian_item_switch_next_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
        }
        if (Variables.currentTemplateIdIdx <= 0) {
            lian_item_switch_prev_btn.isEnabled = false
            lian_item_switch_prev_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
        } else {
            lian_item_switch_prev_btn.isEnabled = true
            lian_item_switch_prev_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
        }
        if (header_action_submit.isEnabled) {
            if (Variables.currentTemplateIdIdx == Variables.availableTemplateIds.size - 1) {
                header_action_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
            } else {
                header_action_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
            }
        }

        lian_item_switch_prev_btn.setOnClickListener {
            turnTo(template, -1)
            loseFocusForEditable(template)
        }

        lian_item_switch_next_btn.setOnClickListener {
            turnTo(template, 1)
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

        if (!template.submitted) {
            header_action_submit.setOnClickListener {
                if (Variables.availableTemplatesMap.size < Variables.availableTemplateIds.size) {
                    Toast.makeText(this, "还未完成所有题目", Toast.LENGTH_SHORT).show()
                } else {
                    var summayrChecked = Variables.availableTemplatesMap.values
                        .filter { Variables.availableTemplateIds.contains(it) }.map { template ->
                            template.submitted = true
                            template.questionsDb!!.map { question ->
                                val answeredMatchResults =
                                    question.optionsDb?.filter { it.correctAnswers() != null }
                                        ?.map { option ->
                                            when (question.type) {
                                                QuestionType.FILL.name -> {
                                                    question.usersAnswers?.get(option.id)?.let {
                                                        option.correctAnswers == it
                                                    }
                                                }
                                                QuestionType.SELECT.name -> {
                                                    if (question.usersAnswers.isNullOrEmpty()) null else question.usersAnswers?.containsKey(
                                                        option.id
                                                    )
                                                }
                                                else -> {
                                                    //暂不支持第三种
                                                    null
                                                }
                                            }
                                        }?.toSet()
                                var result: Boolean? = null
                                answeredMatchResults?.let {
                                    if (it.contains(true)) {
                                        result = true
                                    }
                                    if (it.contains(false)) {
                                        result = false
                                    }
                                }
                                result
                            }.toMutableList()
                        }.flatten().groupBy { it }

                    var right = summayrChecked?.get(true)?.size ?: 0
                    var wrong = summayrChecked?.get(false)?.size ?: 0
                    var missing = summayrChecked?.get(null)?.size ?: 0
                    var rate =
                        (right.toDouble() / (right + wrong + missing).toDouble() * 100).toInt()

                    lian_item_scores.text =
                        "对:$right 错:$wrong 未答:$missing 正确率:${rate}%"
                    lian_item_scores.visibility = View.VISIBLE

                    header_action_submit.isEnabled = false
                    header_action_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)

                    turnTo(
                        Variables.availableTemplatesMap[Variables.availableTemplateIds[Variables.currentTemplateIdIdx]]!!,
                        0
                    )
                }
            }
        }

        lian_item_result_submit.setOnClickListener {
            template.submitted = true
            recycler_view_lian_item_questions.adapter?.notifyDataSetChanged()
            showExplanationForTemplate(template)
            loseFocusForEditable(template)
            lian_item_result_submit.isEnabled = false
            lian_item_result_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
        }

        val templateId = Variables.availableTemplateIds!![Variables.currentTemplateIdIdx]
        Variables.availableTemplatesMap[templateId]?.let {
            updateQuestions(template, template.questionsDb!!)
        } ?: template.practiceQuestions()?.let {

            awaitAsync(
                dataAction = {
                    RoomDB.get(applicationContext).practiceQuestion().getByIds(it)
                        .mapIndexed { qidx, qs ->
                            prepareQuestionData(qs, qidx)
                            qs
                        }.toMutableList()
                },
                uiAction = {
                    updateQuestions(template, it)
                })

        }
        template.itemMainAudioPath?.let {
            mediaPlayer = MediaPlayer.create(this, File(it).toUri())
        }


        if (!template.submitted) {
            template.countDownTimer =
                setUpTimer((template.totalTimeInMinutes * 60 * 1000).toLong())
            template.countDownTimer?.start()
        } else {
            practice_countdown_timer.text = "[已结束]"
            showExplanationForTemplate(template)
        }

    }

    private fun prepareQuestionData(qs: PracticeQuestion, qidx: Int) {
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
    }

    private fun updateQuestions(
        template: PracticeTemplate,
        questions: MutableList<PracticeQuestion>
    ) {
        template.questionsDb = questions

        recycler_view_lian_item_questions.layoutManager =
            GridLayoutManager(this, template.layoutQuestionsPerRow)
        var adapter =
            LianItemQuestionAdapter(template, recycler_view_lian_item_questions, this)
        adapter.data = questions
        recycler_view_lian_item_questions.adapter = adapter

        Variables.availableTemplatesMap[template.id] = template
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

        lian_item_category.text = template.category
        lian_item_scores.text = "共:${right + wrong + missing} 对:$right 错:$wrong 未答:$missing"
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

        } else {
            oldTemplate.countDownTimer?.cancel()
            stopPlayer()
            loadCurrentQuestionTemplate()
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
        } else {
            lian_item_explanations.visibility = View.GONE
            lian_item_main_text.visibility = View.INVISIBLE
            lian_item_main_holder.visibility = View.GONE
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
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        if (header_action_submit.isEnabled) {
            dialog.setTitle("正在答题中,退出答题?")
        } else {
            dialog.setTitle("答题结束,确认退出?")
            dialog.setMessage(lian_item_scores.text)
        }
        dialog.setPositiveButton("退出") { dialog, which ->
            super@LianPage0ActivityClone.onBackPressed()
            stopPlayer()
            Variables.availableTemplateIds.clear()
            Variables.currentTemplateIdIdx = -1
//            Variables.availableTemplatesMap.clear()
        }
        dialog.setNegativeButton("不退出") { dialog, which -> dialog?.dismiss() }
        dialog.show()
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
                v.setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions_red)
                (v as Button).text = "已收藏"
            } else {
                v.setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions_blue)
                (v as Button).text = "收藏"
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
                if (!entered.isNullOrBlank()) {
                    (v as Button).text = "我的笔记"
                    v.setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions_orange)
                } else {
                    (v as Button).text = "添加笔记"
                }
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
                (v as Button).text = "保存笔记"
            }

        }
    }
}