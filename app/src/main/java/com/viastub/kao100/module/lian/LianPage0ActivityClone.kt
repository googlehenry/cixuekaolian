package com.viastub.kao100.module.lian

import android.app.AlertDialog
import android.content.Intent
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
import com.viastub.kao100.beans.KaoContext
import com.viastub.kao100.beans.KaoType
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
        var lianContext = intent.extras?.get("lianContext") as KaoContext


        Variables.kaoContext = lianContext
        Variables.availableTemplateIds =
            sections?.flatMap { it.practiceTemplates() ?: mutableListOf() }.toMutableList()
        Variables.currentTemplateIdIdx =
            if (Variables.availableTemplateIds!!.size > 0) 0 else -1

        //flag last exam answer data loaded if clicked check last exam
        if (Variables.kaoContext?.loadLastExam == true) {
            Variables.kaoContext?.previousExamSimuLoaded = true
        }
        //If user previously clicked last exam, then start new exam, clean user's answers last time
        if (Variables.kaoContext?.loadLastExam == false && Variables.kaoContext?.previousExamSimuLoaded == true) {
            Variables.availableTemplatesMap.values.forEach {
                it.questionsDb?.forEach {
                    it.usersAnswers =
                        mutableMapOf()
                }
            }
            Variables.kaoContext?.previousExamSimuLoaded = false
        }

        if (Variables.kaoContext?.earnedScoresThisTimeTemp == null) {
            Variables.kaoContext?.earnedScoresThisTimeTemp = -1.0 //started, but not submitted
        }

        if (Variables.currentTemplateIdIdx >= 0) {
            //load question from db 1 at a time
            loadCurrentQuestionTemplate()
        }

        Variables.kaoContext?.earnedScoresLastTime?.let {
            Toast.makeText(this, "已恢复上次答题状态", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCurrentQuestionTemplate() {
        val templateId = Variables.availableTemplateIds!![Variables.currentTemplateIdIdx]

        Variables.kaoContext?.earnedScoresThisTimeTemp?.let {
            if (it < 0) {
                //in progress, not submit actually
                Variables.availableTemplatesMap[templateId]?.submitted = false
            }
        }


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

        if (Variables.kaoContext?.loadLastExam == true) {
            template.submitted = true
        }

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
            if (Variables.currentTemplateIdIdx == Variables.availableTemplateIds.size - 1
                && !Variables.kaoContext!!.currentIsPartialQuestions
                && !template.submitted
                && Variables.kaoContext?.earnedScoresThisTimeTemp!! < 0
            ) {
                header_action_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
            } else {

                if (Variables.kaoContext!!.currentIsPartialQuestions && Variables.currentTemplateIdIdx == Variables.availableTemplateIds.size - 1) {
                    header_action_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
                } else {
                    header_action_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
                }
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

        if (!template.submitted && Variables.kaoContext?.earnedScoresThisTimeTemp!! < 0) {
            header_action_submit.setOnClickListener {
                if (Variables.kaoContext!!.currentIsPartialQuestions) {
                    if (Variables.currentTemplateIdIdx < Variables.availableTemplateIds.size - 1) {
                        Toast.makeText(this, "请回答该部分所有问题", Toast.LENGTH_SHORT).show()
                    } else {
                        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
                        dialog.setTitle("完成该部分无法交卷,请先点击[模拟考试]回答完所有题目?")
                        dialog.setPositiveButton("知道了") { dialog, which ->
                            doGoBack()
                        }
                        dialog.show()
                    }
                } else {
                    if (Variables.currentTemplateIdIdx < Variables.availableTemplateIds.size - 1) {
                        Toast.makeText(this, "请回答该部分所有问题", Toast.LENGTH_SHORT).show()
                    } else {
                        checkAnswers()
                    }
                }

            }
        }

        lian_template_result_submit.setOnClickListener {
            template.submitted = true
            recycler_view_lian_item_questions.adapter?.notifyDataSetChanged()
            showExplanationForTemplate(template)
            loseFocusForEditable(template)
            lian_template_result_submit.isEnabled = false
            lian_template_result_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
        }

        Variables.availableTemplatesMap[template.id]?.let {
            if (Variables.kaoContext?.loadLastExam == true) {
                null
            } else {
                updateQuestions(it, it.questionsDb!!)
            }
        } ?: template.practiceQuestions()?.let {
            awaitAsync(
                dataAction = {
                    RoomDB.get(applicationContext).practiceQuestion().getByIds(it)
                        .mapIndexed { qidx, qs ->
                            prepareQuestionData(qs, qidx)
                            qs
                        }
                        .toMutableList()
                },
                uiAction = {
                    updateQuestions(template, it)
                })

        }
        template.itemMainAudioPath?.let {
            mediaPlayer = MediaPlayer.create(this, File(it).toUri())
        }


        if (!template.submitted && Variables.kaoContext?.earnedScoresThisTimeTemp!! < 0) {
            template.countDownTimer =
                setUpTimer((template.totalTimeInMinutes * 60 * 1000).toLong())
            template.countDownTimer?.start()
        } else {
            practice_countdown_timer.text = "[已结束]"
            showExplanationForTemplate(template)
        }

    }

    private fun checkAnswers() {
        var checkedTemplates = Variables.availableTemplatesMap.values
            .filter { Variables.availableTemplateIds.contains(it.id) }
        var summayrChecked = checkedTemplates.map { template ->
            template.submitted = true
            template.questionsDb!!.map { question ->
                var scorePerQuestion =
                    template.totalScore / (template.practiceQuestions()?.size ?: 1)

                val answeredMatchResults =
                    question.optionsDb?.filter { it.correctAnswers() != null }
                        ?.map { option ->
                            when (question.type) {
                                QuestionType.FILL.name -> {
                                    question.usersAnswers?.get(option.id)?.let {
                                        option.correctAnswersSplitByPipes == it
                                    }
                                }
                                QuestionType.SELECT.name -> {
                                    if (question.usersAnswers.isNullOrEmpty()) null else question.usersAnswers?.containsKey(
                                        option.id
                                    )
                                }
                                QuestionType.CORRECT.name -> {
                                    //var correct: Boolean = item.correctAnswers()?.any { ans -> (ans == it) } ?: false
                                    if (question.usersAnswers.isNullOrEmpty()) null else (question.usersAnswers[option.id]?.let {
                                        template.pooledQuestionStandardAnswers().values.flatten()
                                            .contains(it)
                                    } ?: null)
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

                question.scoreEarned =
                    (if (result == true) scorePerQuestion else 0.0)

                result
            }.toMutableList()
        }.flatten().groupBy { it }

        var scoreEarned =
            checkedTemplates.map { it.questionsDb!!.map { it.scoreEarned } }
                .flatten().sum()

        var right = summayrChecked?.get(true)?.size ?: 0
        var wrong = summayrChecked?.get(false)?.size ?: 0
        var missing = summayrChecked?.get(null)?.size ?: 0
        var rate =
            (right.toDouble() / (right + wrong + missing).toDouble() * 100).toInt()

        header_action_submit.isEnabled = false
        header_action_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
        Variables.kaoContext?.earnedScoresThisTimeTemp = scoreEarned

        turnTo(
            Variables.availableTemplatesMap[Variables.availableTemplateIds[Variables.currentTemplateIdIdx]]!!,
            0
        )

        launchAsync {
            if (Variables.kaoContext?.type == KaoType.ExamSimulation) {

                RoomDB.get(applicationContext).myExamSimuHistory().insert(
                    MyExamSimuHistory(
                        Variables.currentUserId,
                        Variables.kaoContext!!.typedEntityId,
                        myScores = scoreEarned,
                        myTotalCorrects = right,
                        myTotalMissing = missing,
                        myTotalWrongs = wrong
                    )
                )

                var answeredHistories =
                    checkedTemplates.flatMap { tempLat ->
                        tempLat.questionsDb?.map {
                            MyQuestionAnsweredHistory(
                                userId = Variables.currentUserId,
                                practiceQuestionId = it.id!!,
                                answerIsCorrect = scoreEarned > 0,
                                optionalPracticeTemplateId = tempLat.id,
                                optionalPracticeTargetId = Variables.kaoContext?.typedEntityId
                            ).setMyAnswersJson(it.usersAnswers)
                        } ?: mutableListOf()
                    }.toMutableList()

                answeredHistories?.let {
                    RoomDB.get(applicationContext).myQuestionAnsweredHistory()
                        .insertAll(it)
                }
            }
        }

        startExamSummaryActivity(scoreEarned, right, wrong, missing, rate)
    }

    private fun startExamSummaryActivity(
        scoreEarned: Double,
        right: Int,
        wrong: Int,
        missing: Int,
        rate: Int
    ) {
        var intent = Intent(this, LianPageScorePageActivity::class.java)
        intent.putExtra("scoreEarned", scoreEarned)
        intent.putExtra("rate", rate)

        intent.putExtra("right", right)
        intent.putExtra("wrong", wrong)
        intent.putExtra("missing", missing)
        startActivity(intent)
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
            .getByQuestionIdsOfUser(Variables.currentUserId, qs.id!!)

        if (myAction == null) {
            RoomDB.get(applicationContext).myQuestionAction()
                .insert(MyQuestionAction(Variables.currentUserId, qs.id!!))

            myAction = RoomDB.get(applicationContext).myQuestionAction()
                .getByQuestionIdsOfUser(Variables.currentUserId, qs.id!!)
        }

        if (Variables.kaoContext?.loadLastExam == true) {
            var answeredHistory = RoomDB.get(applicationContext).myQuestionAnsweredHistory()
                .getByUserIdOfAnsweredHistory(Variables.currentUserId, qs.id!!)

            answeredHistory?.let {
                qs.myQuestionAnsweredHistoryDb = it
                qs.usersAnswers = it.getMyAnswers() ?: mutableMapOf()
            }
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

        Variables.availableTemplatesMap[template.id!!] = template
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
                pq.id!!,
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
        doGoBack()
    }

    private fun doGoBack() {
        super@LianPage0ActivityClone.onBackPressed()
        stopPlayer()
        Variables.availableTemplateIds.clear()
        Variables.currentTemplateIdIdx = -1
    }


    private fun stopPlayer() {
        mediaPlayer?.stop()
        mediaPlayer = null
    }

    fun setUpTimer(milliSeconds: Long): CountDownTimer {
        practice_countdown_timer.setTextColor(Color.parseColor("#ffffff"))

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