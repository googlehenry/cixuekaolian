package com.viastub.kao100.module.lian

import android.content.ClipboardManager
import android.content.Context
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
import com.viastub.kao100.beans.LianContext
import com.viastub.kao100.db.*
import com.viastub.kao100.utils.VariablesKao
import com.viastub.kao100.utils.VariablesLian
import com.viastub.kao100.wigets.TextViewSelectionCallback
import kotlinx.android.synthetic.main.activity_ci_word_detail_page.*
import kotlinx.android.synthetic.main.activity_lian_item_page.*
import kotlinx.android.synthetic.main.activity_lian_item_page.floating_button_add
import kotlinx.android.synthetic.main.activity_lian_item_page.header_back
import java.io.File
import java.util.*


class LianPage0ActivityPractice : BaseActivity(), QuestionActionListener {

    override fun id(): Int {
        return R.layout.activity_lian_item_page
    }


    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        var lianContext = intent?.extras?.get("context") as LianContext
        VariablesLian.lianContext = lianContext

        var templateIds =
            lianContext.sections?.flatMap {
                it.practiceTemplateIds()?.toSortedSet()?.toMutableList() ?: mutableListOf()
            }
                .toMutableList()
        VariablesLian.availableTemplateIds = templateIds


        awaitAsync({
            lianContext.sections?.map {
                it.mySectionPracticeHistory =
                    RoomDB.get(applicationContext).mySectionPracticeHistory()
                        .getByUserIdAndSectionId(VariablesKao.currentUserId, it.id!!)
                it
            }
        }, {
            var latestTemplateDone = lianContext.sections?.flatMap {
                it.mySectionPracticeHistory?.let { it.myFinishedTemplateIds() } ?: sortedSetOf()
            }.maxOrNull()
            val startedIndex = latestTemplateDone?.let {
                templateIds.indexOf(it).let {
                    if (it < 0) 0 else (it + 1).also {
                        Toast.makeText(this, "从上次接着练习", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: 0

            VariablesLian.currentTemplateIdIdx =
                if (VariablesLian.availableTemplateIds!!.size > 0) {
                    if (startedIndex < templateIds.size) {
                        startedIndex
                    } else {
                        templateIds.size - 1
                    }
                } else -1

            if (VariablesLian.currentTemplateIdIdx >= 0) {
                loadCurrentQuestionTemplate()
            }
        })
        floating_button_add.setOnClickListener {
            val cm: ClipboardManager? =
                getSystemService(Context.CLIPBOARD_SERVICE)
                    ?.let { it as ClipboardManager }

            var txt = cm!!.primaryClip?.getItemAt(0)?.text.toString()
//            cm.setPrimaryClip(ClipData.newPlainText("", ""));
            addNewCollectDialog(txt, "练习,手动添加")
        }

    }

    private fun loadCurrentQuestionTemplate(loadReviewMode: Boolean = true) {
        val templateId = VariablesLian.availableTemplateIds!![VariablesLian.currentTemplateIdIdx]

        VariablesLian.lianContext?.sections?.find {
            it.practiceTemplateIds()?.contains(templateId) == true
        }?.let {
            VariablesLian.loadLastTimeSubmittedAnswers =
                (it.mySectionPracticeHistory?.myFinishedTemplateIds() ?: sortedSetOf()).contains(
                    templateId
                )
        }
        if (!loadReviewMode) {
            VariablesLian.loadLastTimeSubmittedAnswers = false
        }

        VariablesLian.availableTemplatesMap[templateId]?.let {
            updateUI(it)
        } ?: awaitAsync(dataAction = {
            var templateDb = RoomDB.get(applicationContext).practiceTemplate()
                .getById(templateId)
            templateDb
        }, uiAction = {
            updateUI(it!!)
        })
    }

    private fun updateUI(template: PracticeTemplate) {


        if (VariablesLian.loadLastTimeSubmittedAnswers) {
            template.submitted = true
        }

        lian_item_seq.text =
            "${VariablesLian.currentTemplateIdIdx + 1}/${VariablesLian.availableTemplateIds.size}"
        teaching_book_practice_progress.max = VariablesLian.availableTemplateIds.size
        teaching_book_practice_progress.secondaryProgress = VariablesLian.currentTemplateIdIdx + 1
        lian_item_category.text = template.category
        lian_item_requirment.text =
            "要求:${template.requirement} (本题${template.totalScore}分,共${template.practiceQuestions()?.size ?: 0}小题,每小题${template.totalScore / (template.practiceQuestions()?.size ?: 1)}分)"
        lian_item_main_text.text = template.itemMainText
        lian_item_main_text.customSelectionActionModeCallback =
            TextViewSelectionCallback(this, lian_item_main_text, "练习,题干")

        lian_item_main_text.visibility =
            if (template.itemMainAudioPath != null) View.GONE else View.VISIBLE
        lian_item_main_audio_start.visibility =
            if (template.itemMainAudioPath != null) View.VISIBLE else View.GONE
        lian_item_main_holder.visibility =
            if (template.itemMainAudioPath == null && template.itemMainText == null) View.GONE else View.VISIBLE

        lian_item_switch_prev_btn.visibility = View.VISIBLE

        lian_item_switch_next_btn.isEnabled = template.submitted
        lian_item_switch_next_btn.visibility = View.VISIBLE

        if (template.submitted) {
            lian_item_switch_next_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
            lian_template_result_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
        } else {
            lian_item_switch_next_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
            lian_template_result_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
        }

        if (VariablesLian.currentTemplateIdIdx > 0 && VariablesLian.availableTemplateIds.size > 1) {
            lian_item_switch_prev_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
            lian_item_switch_prev_btn.isEnabled = true
        } else {
            lian_item_switch_prev_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
            lian_item_switch_prev_btn.isEnabled = false
        }

        lian_item_switch_prev_btn.setOnClickListener {

            turnTo(template, -1)
            loseFocusForEditable(template)
        }

        lian_item_switch_next_btn.setOnClickListener {
            if (template.submitted) {
                turnTo(template, 1)
                loseFocusForEditable(template)
            }
        }

        lian_item_explanations.setOnClickListener {
            loseFocusForEditable(template)
        }

        lian_item_explanations.visibility = View.GONE
        lian_item_explanations.text = template.keyPoints
        lian_item_explanations.customSelectionActionModeCallback =
            TextViewSelectionCallback(this, lian_item_explanations, "练习,解析")

        lian_item_main_audio_start.setOnClickListener {
            template.itemMainAudioPath?.let { plyDemoMp3Reading(it) }
        }

        header_action_submit.visibility = View.GONE
        lian_template_result_submit.visibility = View.VISIBLE

        lian_template_result_submit.setOnClickListener {
            if (!template.submitted) {
                template.submitted = true
                checkAnswers(template)
            } else {
                Toast.makeText(this, "请勿重复提交", Toast.LENGTH_SHORT).show()
            }
        }

        if (template.submitted) {
            header_action_submit.visibility = View.VISIBLE
            header_action_submit.text = "重做该练习"
            header_action_submit.setOnClickListener {
                template.submitted = false
                template.questionsDb?.forEach { it.usersAnswers = mutableMapOf() }
                loadCurrentQuestionTemplate(false)

                header_action_submit.visibility = View.GONE
            }
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
        //if submitted, always load questions from db
        if (template.submitted) {
            refreshQuestionsFromDb(template)
        } else {
            VariablesLian.availableTemplatesMap[template.id]?.let {
                updateQuestions(it, it.questionsDb!!)
            } ?: refreshQuestionsFromDb(template)
        }


    }

    private fun refreshQuestionsFromDb(template: PracticeTemplate) {
        template.practiceQuestions()?.let {
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
    }


    private fun startExamSummaryActivity(
        scoreEarned: Double,
        right: Int,
        wrong: Int,
        missing: Int,
        rate: Int
    ) {
        var intent = Intent(this, LianPageScorePageActivityPractice::class.java)
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
            .getByQuestionIdsOfUser(VariablesKao.currentUserId, qs.id!!)

        if (myAction == null) {
            RoomDB.get(applicationContext).myQuestionAction()
                .insert(MyQuestionAction(VariablesKao.currentUserId, qs.id!!))

            myAction = RoomDB.get(applicationContext).myQuestionAction()
                .getByQuestionIdsOfUser(VariablesKao.currentUserId, qs.id!!)
        }

        if (VariablesLian.loadLastTimeSubmittedAnswers) {
            var answeredHistory = RoomDB.get(applicationContext).myQuestionAnsweredHistory()
                .getByUserIdOfAnsweredHistory(VariablesKao.currentUserId, qs.id!!)

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

        VariablesLian.availableTemplatesMap[template.id!!] = template
    }


    private fun turnTo(oldTemplate: PracticeTemplate, step: Int) {
        //Load next template
        if (step != 0) {
            var toIndex = VariablesLian.currentTemplateIdIdx + step
            when {
                toIndex >= VariablesLian.availableTemplateIds.size -> {
                    VariablesLian.currentTemplateIdIdx =
                        VariablesLian.availableTemplateIds.size - 1
                    Toast.makeText(this, "已到最后一题", Toast.LENGTH_SHORT).show()
                }
                toIndex < 0 -> {
                    VariablesLian.currentTemplateIdIdx = 0
                    Toast.makeText(this, "已到第一题", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //Stop the timer first for previous question
                    oldTemplate.countDownTimer?.cancel()
                    stopPlayer()
                    VariablesLian.currentTemplateIdIdx = toIndex
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
        super@LianPage0ActivityPractice.onBackPressed()
        stopPlayer()
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

            doAsync { RoomDB.get(applicationContext).myQuestionAction().insert(it) }
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
                    doAsync { RoomDB.get(applicationContext).myQuestionAction().insert(it) }
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

    private fun checkAnswers(template: PracticeTemplate) {
        template.submitted = true
        var summayrChecked = template.questionsDb!!.map { question ->
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
        }.toMutableList().groupBy { it }

        var scoreEarned =
            template.let { it.questionsDb!!.map { it.scoreEarned } }.sum()

        var right = summayrChecked?.get(true)?.size ?: 0
        var wrong = summayrChecked?.get(false)?.size ?: 0
        var missing = summayrChecked?.get(null)?.size ?: 0
        var rate =
            (right.toDouble() / (right + wrong + missing).toDouble() * 100).toInt()

        awaitAsync({
            var roomDb = RoomDB.get(applicationContext)

            var answeredHistories = template.questionsDb?.map {
                MyQuestionAnsweredHistory(
                    userId = VariablesKao.currentUserId,
                    practiceQuestionId = it.id!!,
                    answerIsCorrect = scoreEarned > 0,
                    optionalPracticeTemplateId = template.id
                ).setMyAnswersJson(it.usersAnswers)
            } ?: mutableListOf()

            answeredHistories?.toMutableList()?.let {
                roomDb.myQuestionAnsweredHistory()
                    .insertAll(it)
            }

            VariablesLian.lianContext?.sections?.find {
                it.practiceTemplateIds()?.contains(template.id) == true
            }?.let {
                it.mySectionPracticeHistory = roomDb.mySectionPracticeHistory()
                    .getByUserIdAndSectionId(VariablesKao.currentUserId, it.id!!)
                if (it.mySectionPracticeHistory == null) {
                    var his = MySectionPracticeHistory(
                        VariablesKao.currentUserId,
                        it.id!!,
                        template.id.toString()
                    )
                    var myId = roomDb.mySectionPracticeHistory().insert(his).toInt()
                    his.id = myId
                    it.mySectionPracticeHistory = his
                } else {
                    var existingIds =
                        it.mySectionPracticeHistory!!.myFinishedTemplateIds() ?: sortedSetOf()
                    existingIds.add(template.id)

                    it.mySectionPracticeHistory!!.setMyFinishedTemplateIdsSortedString(existingIds.toMutableList())
                    roomDb.mySectionPracticeHistory().insert(it.mySectionPracticeHistory!!)
                }
            }
        }, {
            turnTo(template, 0)
        })

        startExamSummaryActivity(scoreEarned, right, wrong, missing, rate)
    }
}