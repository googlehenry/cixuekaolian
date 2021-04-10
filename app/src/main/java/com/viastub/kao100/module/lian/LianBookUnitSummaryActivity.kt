package com.viastub.kao100.module.lian

import android.content.Intent
import android.net.Uri
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.beans.LianContext
import com.viastub.kao100.db.PracticeBook
import com.viastub.kao100.db.PracticeSection
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

        header_title.text = "单元内容简介"

        var book = intent?.extras?.get("book")?.let { it as PracticeBook }
        var section = intent?.extras?.get("section")?.let { it as PracticeSection }

        summary_book_unit_name.text = "${book?.name}\n${section?.name}"
        book?.coverImage()?.let {
            summary_book_icon.setImageURI(Uri.fromFile(it))
        }

        btn_lian_start.text = "开始练习(随机)"

        btn_lian_start.setOnClickListener {
            startBookSection(book!!, mutableListOf(section!!))
        }

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
        super.onBackPressed()
        VariablesLian.lianContext = null
        VariablesLian.availableTemplatesMap.clear()
        VariablesLian.availableTemplateIds.clear()
        VariablesLian.currentTemplateIdIdx = -1
    }

}