package com.viastub.kao100.module.kao

import android.app.AlertDialog
import android.content.Intent
import android.view.View
import com.viastub.kao100.R
import com.viastub.kao100.adapter.TestSectionAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.beans.LianContext
import com.viastub.kao100.beans.LianType
import com.viastub.kao100.db.ExamSimulation
import com.viastub.kao100.db.PracticeSection
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.module.lian.LianPage0ActivityClone
import com.viastub.kao100.utils.Variables
import kotlinx.android.synthetic.main.activity_kao_exam_summary.*
import kotlinx.android.synthetic.main.activity_kao_exam_summary.header_back
import kotlinx.android.synthetic.main.activity_lian_item_page.*

class KaoExamSummaryActivity : BaseActivity(), View.OnClickListener {

    override fun id(): Int {
        return R.layout.activity_kao_exam_summary
    }


    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        var exam = intent?.extras?.get("exam") as ExamSimulation
        header_title.text = "试卷信息"


        btn_kao_start.setOnClickListener {
            exam?.let {
                awaitAsync(dataAction = {
                    RoomDB.get(applicationContext).practiceSection()
                        .getByIds(it.practiceSections()!!).toMutableList()
                },
                    uiAction = {
                        startExam(exam, it, false)
                    }
                )

            }
        }

        loadDb(exam)
    }

    private fun loadDb(exam: ExamSimulation) {
        exam.let {
            summary_exam_name.text = exam.name
            awaitAsync({
                it.myExamSimuHistory = RoomDB.get(applicationContext).myExamSimuHistory()
                    .getByUserIdOfExam(Variables.currentUserId, it.id)
                it.practiceSectionsDb = it.practiceSections()?.let {
                    RoomDB.get(applicationContext).practiceSection().getByIds(it)
                        ?.mapIndexed { index, practiceSection ->
                            practiceSection.templatesDB =
                                practiceSection.practiceTemplates()?.let {
                                    RoomDB.get(applicationContext).practiceTemplate()
                                        .getByIds(it)?.toMutableList()
                                }

                            practiceSection.displaySeq = index + 1
                            practiceSection
                        }.toMutableList()
                }
                it
            }, {
                updateUI(it)
            })

        }
    }

    override fun onResume() {
        super.onResume()
        Variables.lianContext?.earnedScoresThisTimeTemp?.let {
            if (it >= 0.0) {
                summary_exam_lastScores.visibility = View.VISIBLE
                summary_exam_lastScores.text = "本次得分:${it}"
                btn_kao_start.text = "查看本次结果"
                btn_kao_resume.isEnabled = false
                btn_kao_resume.setBackgroundResource(R.drawable.selector_button_round_cornor_grayed)
            }
        }
    }

    private fun updateUI(exam: ExamSimulation) {

        var sections = exam.practiceSectionsDb!!

        summary_exam_description.text =
            "答题时间:${
                sections.map { it.totalTimeInMinutes() }.sum()
            }分钟, 总分:${sections.map { it.totalScores() }.sum()}分"

        exam.myExamSimuHistory?.let {
            summary_exam_lastScores.visibility = View.VISIBLE
            summary_exam_lastScores.text =
                "上次得分:${it.myScores} (${(it.myTotalCorrects!!.toDouble() / (it.myTotalCorrects!! + it.myTotalWrongs!! + it.myTotalMissing!!).toDouble() * 100).toInt()}%)"

            btn_kao_resume.visibility = View.VISIBLE
            btn_kao_resume.setOnClickListener {
                exam?.let {
                    awaitAsync(dataAction = {
                        RoomDB.get(applicationContext).practiceSection()
                            .getByIds(it.practiceSections()!!).toMutableList()
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
        item?.let {
            startExam(exam, arrayListOf<PracticeSection>(it), true)
        }
    }

    fun startExam(
        exam: ExamSimulation,
        sections: List<PracticeSection>,
        partial: Boolean = false,
        resumeExam: Boolean = false
    ) {
        var intent = Intent(this, LianPage0ActivityClone::class.java)
        var secs = arrayListOf<PracticeSection>()
        secs.addAll(sections)
        var lianContext = LianContext(
            LianType.ExamSimulation,
            exam.id,
            partial,
            earnedScoresThisTimeTemp = Variables.lianContext?.earnedScoresThisTimeTemp,
            earnedScoresLastTime = exam.totalScores() > 0,
            resumeExam = resumeExam,
            previousExamSimuLoaded = (Variables.lianContext?.previousExamSimuLoaded == true)
        )

        intent.putExtra("sections", secs)
        intent.putExtra("lianContext", lianContext)

        startActivity(intent)
    }

    override fun onBackPressed() {
        var tempScore = Variables.lianContext?.earnedScoresThisTimeTemp
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
        Variables.availableTemplateIds.clear()
        Variables.currentTemplateIdIdx = -1
        Variables.availableTemplatesMap.clear()
        Variables.lianContext = null
    }


}