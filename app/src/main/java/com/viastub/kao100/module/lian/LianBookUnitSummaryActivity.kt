package com.viastub.kao100.module.lian

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.viastub.kao100.R
import com.viastub.kao100.adapter.TemplateIDPairAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.beans.LianContext
import com.viastub.kao100.beans.TemplateIDStatus
import com.viastub.kao100.db.PracticeBook
import com.viastub.kao100.db.PracticeSection
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.VariablesKao
import com.viastub.kao100.utils.VariablesLian
import kotlinx.android.synthetic.main.activity_kao_exam_summary.btn_lian_start
import kotlinx.android.synthetic.main.activity_kao_exam_summary.header_back
import kotlinx.android.synthetic.main.activity_kao_exam_summary.header_title
import kotlinx.android.synthetic.main.activity_lian_book_unit_summary.*

class LianBookUnitSummaryActivity : BaseActivity(), View.OnClickListener {

    override fun id(): Int {
        return R.layout.activity_lian_book_unit_summary
    }

    var currentBook: PracticeBook? = null
    var currentSection: PracticeSection? = null

    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        header_title.text = "单元内容"

        currentBook = intent?.extras?.get("book")?.let { it as PracticeBook }
        currentSection = intent?.extras?.get("section")?.let { it as PracticeSection }

    }

    private fun refreshPage() {
        summary_book_unit_name.text = "${currentSection?.name}"
        summary_book_unit_description.visibility =
            if (currentSection?.description.isNullOrBlank()) View.GONE else View.VISIBLE
        currentSection?.description?.let {
            summary_book_unit_description.text = it
        }

        currentBook?.coverImage()?.let {
            if (it.exists()) {
                summary_book_icon.setImageURI(Uri.fromFile(it))
            }
        }

        btn_lian_start.text = "开始练习"

        btn_lian_start.setOnClickListener {
            startBookSection(currentBook!!, mutableListOf(currentSection!!), null)
        }


        //load progress if any
        awaitAsync({
            RoomDB.get(applicationContext).mySectionPracticeHistory()
                .getByUserIdAndSectionId(VariablesKao.currentUserId, currentSection?.id!!)
        }, {
            it?.let {
                progress_holder.visibility = View.VISIBLE
                val max = (currentSection?.practiceTemplateIds()?.size ?: 0)
                val done = (it.myFinishedTemplateIds()?.size ?: 0)
                summary_book_unit_total.text = "共计:${max}大题"
                summary_book_unit_done.text = "完成:${done}"
                summary_book_unit_progress.max = max
                summary_book_unit_progress.secondaryProgress = done
                summary_book_unit_progress.progress = 0//error

            }
            val finishedTemplateIds = it?.myFinishedTemplateIds() ?: setOf<Int>()
            currentSection?.practiceTemplateIds()?.let {

                var pairList =
                    it.sorted().mapIndexed { index, id ->
                        TemplateIDStatus(
                            index + 1,
                            id,
                            finishedTemplateIds.contains(id)
                        )
                    }.toMutableList()
                val adapter = TemplateIDPairAdapter(this)
                adapter.data = pairList
                recycler_view_templateSeqList.adapter = adapter
                recycler_view_templateSeqList.layoutManager = GridLayoutManager(this, 6)
            }
        })
    }

    fun startBookSection(
        book: PracticeBook,
        sections: List<PracticeSection>,
        startedIndex: Int?
    ) {
        var intent = Intent(this, LianPage0ActivityPractice::class.java)
        var secs = arrayListOf<PracticeSection>()
        secs.addAll(sections)
        intent.putExtra("context", LianContext(book, sections.toMutableList(), startedIndex))

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

    override fun onResume() {
        super.onResume()
        refreshPage()
    }

    override fun onClick(v: View?) {
        v?.getTag(R.id.template_seq_holder)?.let { it as TemplateIDStatus }?.let {
            startBookSection(currentBook!!, mutableListOf(currentSection!!), it.seq - 1)
        }
    }

}