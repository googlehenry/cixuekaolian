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
        Variables.lianContext?.earnedScores?.let {
            summary_exam_lastScores.visibility = View.VISIBLE
            summary_exam_lastScores.text = "得分:${it}"
        }
    }

    private fun updateUI(examSimulation: ExamSimulation) {

        var sections = examSimulation.practiceSectionsDb!!

        summary_exam_description.text =
            "答题时间:${
                sections.map { it.totalTimeInMinutes() }.sum()
            }分钟, 总分:${sections.map { it.totalScores() }.sum()}分"

        examSimulation.myExamSimuHistory?.let {
            summary_exam_lastScores.visibility = View.VISIBLE
            summary_exam_lastScores.text = "上次得分:${it.myScores}"
        }

        var adapter = TestSectionAdapter(examSimulation, this)
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
        examSimulation: ExamSimulation,
        sections: List<PracticeSection>,
        partial: Boolean = false
    ) {
        var intent = Intent(this, LianPage0ActivityClone::class.java)
        var secs = arrayListOf<PracticeSection>()
        secs.addAll(sections)
        var lianContext = LianContext(LianType.ExamSimulation, examSimulation.id, partial)

        intent.putExtra("sections", secs)
        intent.putExtra("lianContext", lianContext)

        startActivity(intent)
    }

    override fun onBackPressed() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setTitle("已答题目不会保存,退出考试吗?")
        dialog.setPositiveButton("退出") { dialog, which ->
            super@KaoExamSummaryActivity.onBackPressed()
            Variables.availableTemplateIds.clear()
            Variables.currentTemplateIdIdx = -1
            Variables.availableTemplatesMap.clear()
            Variables.lianContext = null
        }
        dialog.setNegativeButton("不退出") { dialog, which -> dialog?.dismiss() }
        dialog.show()
    }


}