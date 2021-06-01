package com.viastub.kao100.module.kao

import android.app.AlertDialog
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
import com.viastub.kao100.beans.KaoContext
import com.viastub.kao100.db.*
import com.viastub.kao100.utils.VariablesKao
import com.viastub.kao100.wigets.CommonDialog
import com.viastub.kao100.wigets.TextViewSelectionCallback
import kotlinx.android.synthetic.main.activity_ci_word_detail_page.*
import kotlinx.android.synthetic.main.activity_lian_item_page.*
import kotlinx.android.synthetic.main.activity_lian_item_page.floating_button_add
import kotlinx.android.synthetic.main.activity_lian_item_page.header_back
import java.io.File


class KaoPage0ActivityExamination : BaseActivity(), QuestionActionListener {

    override fun id(): Int {
        return R.layout.activity_lian_item_page
    }


    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        var sections = intent?.extras?.get("sections") as ArrayList<PracticeSection>
        var lianContext = intent.extras?.get("lianContext") as KaoContext


        VariablesKao.kaoContext = lianContext
        VariablesKao.availableTemplateIds =
            sections?.flatMap { it.practiceTemplateIds() ?: mutableListOf() }.toMutableList()
        VariablesKao.currentTemplateIdIdx =
            if (VariablesKao.currentTemplateIdIdx >= 0 && VariablesKao.currentTemplateIdIdx <= (VariablesKao.availableTemplateIds.size - 1)) VariablesKao.currentTemplateIdIdx else (if (VariablesKao.availableTemplateIds!!.size > 0) 0 else -1)

        //flag last exam answer data loaded if clicked check last exam
        if (VariablesKao.kaoContext?.loadLastExam == true) {
            VariablesKao.kaoContext?.previousExamSimuLoaded = true
        }
        //If user previously clicked last exam, then start new exam, clean user's answers last time
        if (VariablesKao.kaoContext?.loadLastExam == false && VariablesKao.kaoContext?.previousExamSimuLoaded == true) {
            VariablesKao.availableTemplatesMap.values.forEach {
                it.questionsDb?.forEach {
                    it.usersAnswers =
                        mutableMapOf()
                }
            }
            VariablesKao.kaoContext?.previousExamSimuLoaded = false
        }

        if (VariablesKao.kaoContext?.earnedScoresThisTimeTemp == null) {
            VariablesKao.kaoContext?.earnedScoresThisTimeTemp = -1.0 //started, but not submitted
        }

        if (VariablesKao.currentTemplateIdIdx >= 0) {
            //load question from db 1 at a time
            loadCurrentQuestionTemplate()
        }

        floating_button_add.setOnClickListener {
            val cm: ClipboardManager? =
                getSystemService(Context.CLIPBOARD_SERVICE)
                    ?.let { it as ClipboardManager }

            var txt = cm!!.primaryClip?.getItemAt(0)?.text.toString()
            addNewCollectDialog(txt, "考试,手动添加")
        }
    }

    private fun loadCurrentQuestionTemplate() {
        val templateId = VariablesKao.availableTemplateIds!![VariablesKao.currentTemplateIdIdx]

        VariablesKao.kaoContext?.earnedScoresThisTimeTemp?.let {
            if (it < 0) {
                //in progress, not submit actually
                VariablesKao.availableTemplatesMap[templateId]?.submitted = false
            }
        }


        VariablesKao.availableTemplatesMap[templateId]?.let {
            updateUI(it)
        } ?: awaitAsync(dataAction = {
            RoomDB.get(applicationContext).practiceTemplate()
                .getById(templateId)
        }, uiAction = {
            updateUI(it!!)
        })
    }

    private fun updateUI(template: PracticeTemplate) {
        template.itemMainAudioPath =
            if (template.itemMainAudioPath.isNullOrBlank()) null else template.itemMainAudioPath

        if (VariablesKao.kaoContext?.loadLastExam == true) {
            template.submitted = true
        }

        lian_item_seq.text =
            "${VariablesKao.currentTemplateIdIdx + 1}/${VariablesKao.availableTemplateIds.size}"
        teaching_book_practice_progress.max = VariablesKao.availableTemplateIds.size
        teaching_book_practice_progress.secondaryProgress = VariablesKao.currentTemplateIdIdx + 1

        lian_item_category.text = template.category
        lian_item_requirment.text =
            "要求:${template.requirement} (本题${template.totalScore}分,共${template.practiceQuestions()?.size ?: 0}小题,每小题${template.totalScore / (template.practiceQuestions()?.size ?: 1)}分)"
        lian_item_main_text.text = template.itemMainText
        lian_item_main_text.customSelectionActionModeCallback =
            TextViewSelectionCallback(this, lian_item_main_text, "试卷,题干")

        lian_item_main_text.visibility =
            if (!template.itemMainAudioPath.isNullOrBlank()) View.GONE else View.VISIBLE
        lian_item_main_audio_start.visibility =
            if (!template.itemMainAudioPath.isNullOrBlank()) View.VISIBLE else View.GONE
        lian_item_main_holder.visibility =
            if (template.itemMainAudioPath.isNullOrBlank() && template.itemMainText.isNullOrBlank()) View.GONE else View.VISIBLE

        lian_item_switch_next_btn.visibility = View.GONE
        if (VariablesKao.currentTemplateIdIdx >= 0 && VariablesKao.currentTemplateIdIdx < VariablesKao.availableTemplateIds.size - 1) {
            lian_item_switch_next_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
            lian_item_switch_next_btn.visibility = View.VISIBLE
        }

        lian_item_switch_prev_btn.visibility = View.GONE
        if (VariablesKao.currentTemplateIdIdx > 0 && VariablesKao.currentTemplateIdIdx <= VariablesKao.availableTemplateIds.size - 1) {
            lian_item_switch_prev_btn.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
            lian_item_switch_prev_btn.visibility = View.VISIBLE
        }

        if (header_action_submit.isEnabled) {
            if (VariablesKao.currentTemplateIdIdx == VariablesKao.availableTemplateIds.size - 1
                && !VariablesKao.kaoContext!!.currentIsPartialQuestions
                && !template.submitted
                && VariablesKao.kaoContext?.earnedScoresThisTimeTemp!! < 0
            ) {
                header_action_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_orange)
            } else {

                if (VariablesKao.kaoContext!!.currentIsPartialQuestions && VariablesKao.currentTemplateIdIdx == VariablesKao.availableTemplateIds.size - 1) {
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
        lian_item_explanations.customSelectionActionModeCallback =
            TextViewSelectionCallback(this, lian_item_explanations, "试卷,解析")

        lian_item_main_audio_start.setOnClickListener {
            template.itemMainAudioPath?.let { if (!it.isNullOrBlank()) plyDemoMp3Reading(it) }
        }

        if (!template.submitted && VariablesKao.kaoContext?.earnedScoresThisTimeTemp!! < 0) {
            header_action_submit.setOnClickListener {
                if (VariablesKao.kaoContext!!.currentIsPartialQuestions) {
                    if (VariablesKao.currentTemplateIdIdx < VariablesKao.availableTemplateIds.size - 1) {
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
                    if (VariablesKao.currentTemplateIdIdx < VariablesKao.availableTemplateIds.size - 1) {
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

        VariablesKao.availableTemplatesMap[template.id]?.let {
            if (VariablesKao.kaoContext?.loadLastExam == true) {
                null
            } else {
                updateQuestions(it, it.questionsDb!!)
            }
        } ?: template.practiceQuestions()?.let {
            awaitAsync(
                dataAction = {
                    RoomDB.get(applicationContext).practiceQuestion().getByIds(it)
                        .mapIndexed { qidx, qs ->
                            prepareQuestionData(qs, qidx, template)
                            qs
                        }
                        .toMutableList()
                },
                uiAction = {
                    updateQuestions(template, it)
                })

        }
        template.itemMainAudioPath?.let {
            if (it.isNotBlank()) {
                mediaPlayer = MediaPlayer.create(this, File(it).toUri())
                mediaPlayer!!.setOnCompletionListener {
                    lian_item_main_audio_start.setImageResource(R.drawable.shape_speaker_light)
                }
                mediaPlayer!!.setOnErrorListener { mp, what, extra ->
                    lian_item_main_audio_start.setImageResource(R.drawable.shape_speaker_light)
                    false
                }
            }
        }


        if (!template.submitted && VariablesKao.kaoContext?.earnedScoresThisTimeTemp!! < 0) {
            template.countDownTimer =
                setUpTimer((template.totalTimeInMinutes * 60 * 1000).toLong())
            template.countDownTimer?.start()
            floating_button_score_holder.visibility = View.GONE
        } else {
            floating_button_score_holder.visibility = View.VISIBLE
            var score: Double = VariablesKao.kaoContext?.earnedScoresThisTimeTemp ?: -1.0
            if (score < 0) score = VariablesKao.kaoContext?.earnedScoresLastTime ?: -1.0
            if (score >= 0) {
                floating_button_score.text = String.format("%.1f", score)
            }
            practice_countdown_timer.text = "[已结束]"
            showExplanationForTemplate(template)
        }

    }

    private fun checkAnswers() {
        var checkedTemplates = VariablesKao.availableTemplatesMap.values
            .filter { VariablesKao.availableTemplateIds.contains(it.id) }
        val totalScoreX = checkedTemplates.map { it.totalScore }.sum().toInt()

        var summayrChecked = checkedTemplates.map { template ->
            template.submitted = true
            template.questionsDb!!.map { question ->
                var scorePerQuestion =
                    template.totalScore / (template.practiceQuestions()?.size ?: 1)

                val answeredMatchResults =
                    question.optionsDb?.filter { !it.correctAnswers().isNullOrEmpty() }
                        ?.map { option ->
                            when (question.type) {
                                QuestionType.FILL.name -> {
                                    question.usersAnswers?.get(option.id)?.let {
                                        option.correctAnswers()?.contains(it)
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
                                QuestionType.CORRECT_SINGLE.name -> {
                                    //var correct: Boolean = item.correctAnswers()?.any { ans -> (ans == it) } ?: false
                                    if (question.usersAnswers.isNullOrEmpty()) null else (question.usersAnswers[option.id]?.let {
                                        option.correctAnswers()?.contains(it)
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

                question.checkAnswerResultCorrect = result

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

        floating_button_score.text = "${String.format("%.1f", scoreEarned)}"
        floating_button_score_holder.visibility = View.VISIBLE

        floating_button_score.setOnClickListener {
            val dialog = CommonDialog(this)
            dialog
                .setTitle("考试得分")
                .setReadOnly(true)
                .setMessage(
                    """
                    得分: ${scoreEarned.toInt()}
                    总分: ${totalScoreX}
                    -----
                    正确:${right}道 错误:${wrong} 未答:${missing}
                """.trimIndent()
                )
                .setSingle(true)
                .setOnClickBottomListener(object : CommonDialog.OnClickBottomListener {
                    override fun onNegtiveClick() {
                        dialog.dismiss()
                    }

                    override fun onPositiveClick() {
                        dialog.dismiss()
                    }
                })
            dialog.show()
            dialog.setCanceledOnTouchOutside(true)
        }

        header_action_submit.isEnabled = false
        header_action_submit.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
        VariablesKao.kaoContext?.earnedScoresThisTimeTemp = scoreEarned

        turnTo(
            VariablesKao.availableTemplatesMap[VariablesKao.availableTemplateIds[VariablesKao.currentTemplateIdIdx]]!!,
            0
        )

        doAsync {
            var roomDB = RoomDB.get(applicationContext)

            roomDB.myExamSimuHistory().insert(
                MyExamSimuHistory(
                    VariablesKao.currentUserId,
                    VariablesKao.kaoContext!!.typedEntityId,
                    myScores = scoreEarned,
                    myTotalCorrects = right,
                    myTotalMissing = missing,
                    myTotalWrongs = wrong
                )
            )

            var answeredHistories =
                checkedTemplates.flatMap { tempLat ->
                    tempLat.questionsDb?.map {

                        var questionHistoryRecord = roomDB.myQuestionAnsweredHistory()
                            .getByUserAndQuestionId(VariablesKao.currentUserId, it.id!!)
                            ?: MyQuestionAnsweredHistory(
                                userId = VariablesKao.currentUserId,
                                practiceQuestionId = it.id!!,
                                practiceTemplateId = tempLat.id
                            )

                        questionHistoryRecord.setMyAnswersJson(it.usersAnswers)
                        questionHistoryRecord.answerIsCorrect = it.checkAnswerResultCorrect

                        when (it.checkAnswerResultCorrect) {
                            null -> questionHistoryRecord.skippedAttemptNo += 1
                            true -> questionHistoryRecord.correctAttemptNo += 1
                            false -> questionHistoryRecord.wrongAttemptNo += 1
                        }
                        questionHistoryRecord
                    } ?: mutableListOf()
                }.toMutableList()

            answeredHistories?.let {
                RoomDB.get(applicationContext).myQuestionAnsweredHistory()
                    .insertAll(it)
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
        var intent = Intent(this, KaoPageScorePageActivityExam::class.java)
        intent.putExtra("scoreEarned", scoreEarned)
        intent.putExtra("rate", rate)

        intent.putExtra("right", right)
        intent.putExtra("wrong", wrong)
        intent.putExtra("missing", missing)
        startActivity(intent)
    }

    private fun prepareQuestionData(qs: PracticeQuestion, qidx: Int, template: PracticeTemplate) {
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
                .insert(
                    MyQuestionAction(
                        VariablesKao.currentUserId,
                        qs.id!!,
                        practiceTemplateId = template.id
                    )
                )

            myAction = RoomDB.get(applicationContext).myQuestionAction()
                .getByQuestionIdsOfUser(VariablesKao.currentUserId, qs.id!!)
        }

        if (VariablesKao.kaoContext?.loadLastExam == true) {
            var answeredHistory = RoomDB.get(applicationContext).myQuestionAnsweredHistory()
                .getByUserAndQuestionId(VariablesKao.currentUserId, qs.id!!)

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

        VariablesKao.availableTemplatesMap[template.id!!] = template
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
                VariablesKao.currentUserId,
                pq.id!!,
                answerIsCorrect = result,
                practiceTemplateId = template.id
            ).setMyAnswersJson(pq.usersAnswers)
        }?.toMutableList()

        answeredHistoryForThisTemplate?.let {
            doAsync {
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
            var toIndex = VariablesKao.currentTemplateIdIdx + step
            when {
                toIndex >= VariablesKao.availableTemplateIds.size -> {
                    VariablesKao.currentTemplateIdIdx =
                        VariablesKao.availableTemplateIds.size - 1
                    Toast.makeText(this, "已到最后一题", Toast.LENGTH_SHORT).show()
                }
                toIndex < 0 -> {
                    VariablesKao.currentTemplateIdIdx = 0
                    Toast.makeText(this, "已到第一题", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //Stop the timer first for previous question
                    oldTemplate.countDownTimer?.cancel()
                    stopPlayer()
                    VariablesKao.currentTemplateIdIdx = toIndex
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
                if (template.itemMainAudioPath.isNullOrBlank() && template.itemMainText.isNullOrBlank()) View.GONE else View.VISIBLE
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
            lian_item_main_audio_start.setImageResource(R.drawable.shape_speaker_light)
        } else {
            mediaPlayer?.start()
            lian_item_main_audio_start.setImageResource(R.drawable.shape_speaker_red)
        }
    }

    override fun onBackPressed() {
        doGoBack()
    }

    private fun doGoBack() {
        super@KaoPage0ActivityExamination.onBackPressed()
        stopPlayer()
//        VariablesKao.availableTemplateIds.clear()
//        VariablesKao.currentTemplateIdIdx = -1
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

    override fun errorButtonClicked(
        v: View?,
        myQuestionAnsweredHistoryDb: MyQuestionAnsweredHistory?
    ) {
        val dialog = CommonDialog(this)
        var errobook = (v?.let { it as Button })

        dialog
            .setTitle("移除错题!")
            .setReadOnly(true)
            .setMessage("当前已收录于错题本，被移除错题后你将无法再将其加入,除非下次做题过程中再次犯错,确定吗?")
            .setSingle(false)
            .setOnClickBottomListener(object : CommonDialog.OnClickBottomListener {
                override fun onPositiveClick() {
                    myQuestionAnsweredHistoryDb?.let {
                        doAsync {
                            it.wrongAttemptNo = 0
                            RoomDB.get(applicationContext).myQuestionAnsweredHistory().insert(it)
                        }
                        errobook?.visibility = View.GONE
                    }
                    dialog.dismiss()
                }

                override fun onNegtiveClick() {
                    dialog.dismiss()
                }
            })
        dialog.show()
        dialog.setCanceledOnTouchOutside(true)
    }

    override fun favoriteButtonClicked(v: View, myAction: MyQuestionAction?) {
        myAction?.let {
            it.isFavorite = (it.isFavorite != true)

            if (it.isFavorite == true) {
                (v as Button).text = "已收藏"
                (v as Button).setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions_red)
                Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show()
            } else {
                (v as Button).text = "收藏"
                (v as Button).setBackgroundResource(R.drawable.selector_button_round_cornor_question_functions_blue)
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
                    (v as Button).text = "查看笔记"
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