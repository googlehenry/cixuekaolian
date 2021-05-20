package com.viastub.kao100.module.lian

import android.content.Intent
import android.net.Uri
import android.view.View
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.beans.LianContext
import com.viastub.kao100.db.PracticeBook
import com.viastub.kao100.db.PracticeSection
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.VariablesKao
import com.viastub.kao100.utils.VariablesLian
import kotlinx.android.synthetic.main.activity_kao_exam_summary.btn_lian_start
import kotlinx.android.synthetic.main.activity_kao_exam_summary.header_back
import kotlinx.android.synthetic.main.activity_kao_exam_summary.header_title
import kotlinx.android.synthetic.main.activity_lian_book_unit_summary.*

class LianBookUnitSummaryActivity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_lian_book_unit_summary
    }


    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        header_title.text = "单元内容"

        var book = intent?.extras?.get("book")?.let { it as PracticeBook }
        var section = intent?.extras?.get("section")?.let { it as PracticeSection }

        summary_book_unit_name.text = "${section?.name}"
        summary_book_unit_description.visibility =
            if (section?.description.isNullOrBlank()) View.GONE else View.VISIBLE
        section?.description?.let {
            summary_book_unit_description.text = it
        }
        book?.coverImage()?.let {
            if (it.exists()) {
                summary_book_icon.setImageURI(Uri.fromFile(it))
            }
        }

        btn_lian_start.text = "开始练习"

        btn_lian_start.setOnClickListener {
            startBookSection(book!!, mutableListOf(section!!))
        }

        //load progress if any
        awaitAsync({
            RoomDB.get(applicationContext).mySectionPracticeHistory()
                .getByUserIdAndSectionId(VariablesKao.currentUserId, section?.id!!)
        }, {
            it?.let {
                progress_holder.visibility = View.VISIBLE
                val max = (section?.practiceTemplateIds()?.size ?: 0)
                val done = (it.myFinishedTemplateIds()?.size ?: 0)
                summary_book_unit_total.text = "共计:${max}大题"
                summary_book_unit_done.text = "完成:${done}"
                summary_book_unit_progress.max = max
                summary_book_unit_progress.secondaryProgress = done
                summary_book_unit_progress.progress = 0//error
            }
        })
    }

    fun startBookSection(
        book: PracticeBook,
        sections: List<PracticeSection>
    ) {
        var intent = Intent(this, LianPage0ActivityPractice::class.java)
        var secs = arrayListOf<PracticeSection>()
        secs.addAll(sections)
        intent.putExtra("context", LianContext(book, sections.toMutableList()))

        startActivity(intent)
    }

    override fun onBackPressed() {
        doGoBack()
    }

    private fun doGoBack() {
        super.onBackPressed()
        VariablesLian.lianContext = null
        VariablesLian.availableTemplatesMap.clear()
        VariablesLian.availableTemplateIds.clear()
        VariablesLian.currentTemplateIdIdx = -1
    }

}