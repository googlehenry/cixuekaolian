package com.viastub.kao100.module.kao

import android.app.AlertDialog
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton
import com.viastub.kao100.R
import com.viastub.kao100.adapter.TestSectionAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.beans.KaoContext
import com.viastub.kao100.beans.KaoType
import com.viastub.kao100.db.ExamSimulation
import com.viastub.kao100.db.PracticeSection
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.module.my.MyCiHistoryPageActivity
import com.viastub.kao100.module.my.MyCollectionHistoryPageActivity
import com.viastub.kao100.module.my.MyPracticeHistoryPageActivity
import com.viastub.kao100.utils.VariablesKao
import kotlinx.android.synthetic.main.activity_kao_exam_summary.*


class KaoExamSummaryActivity : BaseActivity(), View.OnClickListener {

    override fun id(): Int {
        return R.layout.activity_kao_exam_summary
    }


    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        var exam = intent?.extras?.get("exam") as ExamSimulation
        header_title.text = "试卷信息"


        floatingButtonMenusSetup()


        btn_lian_start.setOnClickListener {
            exam?.let {
                awaitAsync(dataAction = {
                    RoomDB.get(applicationContext).practiceSection()
                        .getByIds(it.practiceSections()!!)?.toMutableList() ?: mutableListOf()
                },
                    uiAction = {
                        startExam(exam, it, false)
                    }
                )
            }
        }

        loadDb(exam)
    }

    private fun floatingButtonMenusSetup() {

        val itemBuilder = SubActionButton.Builder(this)
        itemBuilder.setBackgroundDrawable(
            BitmapDrawable(
                resources,
                BitmapFactory.decodeResource(resources, R.drawable.shape_button_round_white)
            )
        )

        val actionMenu = FloatingActionMenu.Builder(this)
            .addSubActionView(
                itemBuilder
                    .setContentView(ImageView(this).also {
                        it.isClickable = true
                        it.imageTintList = resources.getColorStateList(R.color.colorPrimary, null)
                        it.setImageResource(R.drawable.ic_func_dictionary)
                        it.setOnTouchListener { view: View, motionEvent: MotionEvent ->
                            when (motionEvent.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    startActivity(
                                        Intent(
                                            this,
                                            MyCiHistoryPageActivity::class.java
                                        )
                                    )
                                }
                            }
                            false
                        }
                    }).build()
            )
            .addSubActionView(itemBuilder.setContentView(ImageView(this).also {
                it.isClickable = true
                it.imageTintList = resources.getColorStateList(R.color.colorPrimary, null)
                it.setImageResource(R.drawable.ic_func_textbook)
                it.setOnTouchListener { view: View, motionEvent: MotionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startActivity(
                                Intent(
                                    this,
                                    MyCollectionHistoryPageActivity::class.java
                                )
                            )
                        }
                    }
                    false
                }
            }).build())
            .addSubActionView(itemBuilder.setContentView(ImageView(this).also {
                it.isClickable = true
                it.imageTintList = resources.getColorStateList(R.color.colorPrimary, null)
                it.setImageResource(R.drawable.ic_func_practice)
                it.setOnTouchListener { view: View, motionEvent: MotionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startActivity(
                                Intent(
                                    this,
                                    MyPracticeHistoryPageActivity::class.java
                                )
                            )
                        }
                    }
                    false
                }
            }).build())
            .addSubActionView(
                itemBuilder
                    .setContentView(ImageView(this).also {
                        it.isClickable = true
                        it.imageTintList = resources.getColorStateList(R.color.colorPrimary, null)
                        it.setImageResource(R.drawable.icon_button_plus)

                        it.setOnTouchListener { view: View, motionEvent: MotionEvent ->
                            when (motionEvent.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    val cm: ClipboardManager? =
                                        getSystemService(Context.CLIPBOARD_SERVICE)
                                            ?.let { it as ClipboardManager }

                                    var txt = cm!!.primaryClip?.getItemAt(0)?.text.toString()
                                    addNewCollectDialog(txt, "试卷简介,手动添加")
                                }
                            }
                            false
                        }
                    }).build()
            )
            .setStartAngle(180)
            .setEndAngle(270)
            .attachTo(floating_buttons_menus)
            .build()

    }

    private fun loadDb(exam: ExamSimulation) {
        exam.let {
            summary_exam_name.text = exam.name
            awaitAsync({
                it.myExamSimuHistory = RoomDB.get(applicationContext).myExamSimuHistory()
                    .getByUserIdOfExam(VariablesKao.currentUserId, it.id)
                it.practiceSectionsDb = it.practiceSections()?.let {
                    RoomDB.get(applicationContext).practiceSection().getByIds(it)
                        ?.mapIndexed { index, practiceSection ->
                            practiceSection.templatesDB =
                                practiceSection.practiceTemplateIds()?.let {
                                    RoomDB.get(applicationContext).practiceTemplate()
                                        .getByIds(it)?.toMutableList()
                                }

                            practiceSection.displaySeq = index + 1
                            practiceSection
                        }?.toMutableList() ?: mutableListOf()
                }
                it
            }, {
                updateUI(it)
            })

        }
    }

    override fun onResume() {
        super.onResume()
        VariablesKao.kaoContext?.earnedScoresThisTimeTemp?.let {
            if (it >= 0.0) {
                floating_button_score_holder.visibility = View.VISIBLE
                floating_button_score_id.text = "本次"
                floating_button_score.text = String.format("%.1f", it)

                btn_lian_start.text = "查看本次结果"
                btn_kao_resume.isEnabled = false
                btn_kao_resume.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
            }
        }
    }

    var lastScore: Double? = null

    private fun updateUI(exam: ExamSimulation) {

        var sections = exam.practiceSectionsDb!!

        summary_exam_description.text =
            "答题时间:${
                sections.map { it.totalTimeInMinutes() }.sum()
            }分钟, 总分:${sections.map { it.totalScores() }.sum()}分"

        exam.myExamSimuHistory?.let {
            lastScore = it.myScores
            floating_button_score_holder.visibility = View.VISIBLE
            floating_button_score_id.text = "上次"
            floating_button_score.text = String.format("%.1f", it.myScores)

            btn_kao_resume.visibility = View.VISIBLE
            btn_kao_resume.setOnClickListener {
                exam?.let {
                    awaitAsync(
                        dataAction = {
                            RoomDB.get(applicationContext).practiceSection()
                                .getByIds(it.practiceSections()!!)?.toMutableList()
                                ?: mutableListOf()
                        },
                        uiAction = {
                            startExam(exam, it, false, resumeExam = true)
                        }
                    )

                }
            }
        }

        var adapter = TestSectionAdapter(exam, this)
        adapter.data = sections.toMutableList()
        recycler_view_exam_sections.adapter = adapter
    }

    override fun onClick(v: View?) {
        var item = v?.getTag(R.id.section_item_holder) as PracticeSection
        var exam = v?.getTag(-1) as ExamSimulation

        Toast.makeText(this, "请点击模拟考试", Toast.LENGTH_SHORT).show()
    }

    fun startExam(
        exam: ExamSimulation,
        sections: List<PracticeSection>,
        partial: Boolean = false,
        resumeExam: Boolean = false
    ) {
        var intent = Intent(this, KaoPage0ActivityExamination::class.java)
        var secs = arrayListOf<PracticeSection>()
        secs.addAll(sections)
        var lianContext = KaoContext(
            KaoType.ExamSimulation,
            exam.id,
            partial,
            earnedScoresThisTimeTemp = VariablesKao.kaoContext?.earnedScoresThisTimeTemp,
            earnedScoresLastTime = lastScore,
            loadLastExam = resumeExam,
            previousExamSimuLoaded = (VariablesKao.kaoContext?.previousExamSimuLoaded == true)
        )

        intent.putExtra("sections", secs)
        intent.putExtra("lianContext", lianContext)

        startActivity(intent)
    }

    override fun onBackPressed() {
        var tempScore = VariablesKao.kaoContext?.earnedScoresThisTimeTemp
        if (tempScore != null && tempScore < 0.0) {
            val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
            dialog.setTitle("未交卷题目不会保存,退出考试吗?")
            dialog.setPositiveButton("退出") { dialog, which ->
                doGoBack()
            }
            dialog.setNegativeButton("不退出") { dialog, which -> dialog?.dismiss() }
            dialog.show()
        } else {
            doGoBack()
        }
    }

    private fun doGoBack() {
        super@KaoExamSummaryActivity.onBackPressed()
        VariablesKao.availableTemplateIds.clear()
        VariablesKao.currentTemplateIdIdx = -1
        VariablesKao.availableTemplatesMap.clear()
        VariablesKao.kaoContext = null
    }


}