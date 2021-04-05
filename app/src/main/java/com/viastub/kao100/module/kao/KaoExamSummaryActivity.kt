package com.viastub.kao100.module.kao

import android.content.Intent
import android.view.View
import com.viastub.kao100.R
import com.viastub.kao100.adapter.TestSectionAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.ExamSimulation
import com.viastub.kao100.db.PracticeSection
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.module.lian.LianPage0ActivityClone
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

        btn_kao_start.setOnClickListener {
            exam?.let {
                awaitAsync(dataAction = {
                    RoomDB.get(applicationContext).practiceSection()
                        .getByIds(it.practiceSections()!!).toMutableList()
                },
                    uiAction = {
                        startExam(it)
                    }
                )

            }
        }

        exam.let {
            summary_exam_name.text = exam.name
            exam.practiceSections()?.let {
                awaitAsync(
                    dataAction = {
                        RoomDB.get(applicationContext).practiceSection().getByIds(it)
                            ?.mapIndexed { index, practiceSection ->
                                practiceSection.displaySeq = index + 1
                                practiceSection
                            }
                    },
                    uiAction = {
                        updateUI(it)
                    }

                )
            }

        }

    }

    private fun updateUI(sections: List<PracticeSection>) {

        summary_exam_description.text =
            "总分:${sections.map { it.score }.sum()}分,时间:${
                sections.map { it.totalTimeInMinutes }.sum()
            }分钟"

        var adapter = TestSectionAdapter(this)
        adapter.data = sections.toMutableList()
        recycler_view_exam_sections.adapter = adapter
    }

    override fun onClick(v: View?) {
        var item = v?.getTag(R.id.section_item_holder) as PracticeSection
        item?.let {
            startExam(arrayListOf<PracticeSection>(it))
        }
    }

    fun startExam(sections: List<PracticeSection>) {
        var intent = Intent(this, LianPage0ActivityClone::class.java)
        var secs = arrayListOf<PracticeSection>()
        secs.addAll(sections)
        intent.putExtra("sections", secs)
        startActivity(intent)
    }

}