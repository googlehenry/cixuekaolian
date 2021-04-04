package com.viastub.kao100.module.lian

import android.media.MediaPlayer
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.viastub.kao100.R
import com.viastub.kao100.adapter.LianItemQuestionAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.PracticeQuestionTemplate
import com.viastub.kao100.db.PracticeSection
import com.viastub.kao100.db.RoomDB
import kotlinx.android.synthetic.main.activity_lian_item_page.*

class LianPage0ActivityClone : BaseActivity() {
    companion object {
        var currentQuestionTemplateIdIdx: Int = -1
        lateinit var availableQuestionTemplateIds: MutableList<Int>
    }

    override fun id(): Int {
        return R.layout.activity_lian_item_page
    }


    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        var sections = intent?.extras?.get("sections") as ArrayList<PracticeSection>

        availableQuestionTemplateIds =
            sections?.flatMap { it.practiceQuestionTemplates() ?: mutableListOf() }.toMutableList()
        currentQuestionTemplateIdIdx = if (availableQuestionTemplateIds!!.size > 0) 0 else -1

        if (currentQuestionTemplateIdIdx >= 0) {
            //load question from db 1 at a time
            doAsync(dataAction = {
                RoomDB.get(applicationContext).practiceQuestionTemplate()
                    .getById(availableQuestionTemplateIds!![currentQuestionTemplateIdIdx])
            }, uiAction = {
                updateUI(it)
            })
        }

    }

    private fun updateUI(questionTemplate: PracticeQuestionTemplate) {


        lian_item_category.text = questionTemplate.category
        lian_item_requirment.text = questionTemplate.requirement
        lian_item_main_text.text = questionTemplate.itemMainText

        lian_item_main_text.visibility =
            if (questionTemplate.itemMainAudio != null) View.GONE else View.VISIBLE
        lian_item_main_audio_start.visibility =
            if (questionTemplate.itemMainAudio != null) View.VISIBLE else View.GONE
        lian_item_main_holder.visibility =
            if (questionTemplate.itemMainAudio == null && questionTemplate.itemMainText == null) View.GONE else View.VISIBLE


        lian_item_show_review_btn.setOnClickListener {
            lian_item_explanations.visibility =
                if (lian_item_explanations.isVisible) View.INVISIBLE else View.VISIBLE
            if (!lian_item_main_text.isVisible) {
                lian_item_main_text.visibility = View.VISIBLE
            }
            if (!lian_item_main_holder.isVisible) {
                lian_item_main_holder.visibility =
                    if (questionTemplate.itemMainAudio == null && questionTemplate.itemMainText == null) View.GONE else View.VISIBLE
            }
        }

        lian_item_explanations.visibility = View.INVISIBLE
        lian_item_explanations.text = questionTemplate.keyPoints

        lian_item_main_audio_start.setOnClickListener { plyDemoMp3Reading() }

        lian_item_result_submit.setOnClickListener {
            questionTemplate.submitted = true
            recycler_view_lian_item_questions.adapter?.notifyDataSetChanged()
        }


        questionTemplate.practiceQuestions()?.let {

            doAsync(dataAction = {
                RoomDB.get(applicationContext).practiceQuestion().getByIds(it).map { qs ->
                    var options = RoomDB.get(applicationContext).practiceAnswerOption()
                        .getByIds(qs.optionPractices()!!).toMutableList()

                    qs.optionsDb = options
                    qs
                }.toMutableList()
            },
                uiAction = {

                    recycler_view_lian_item_questions.layoutManager =
                        GridLayoutManager(this, questionTemplate.layoutQuestionsPerRow)
                    var adapter =
                        LianItemQuestionAdapter(questionTemplate, recycler_view_lian_item_questions)
                    adapter.data = it
                    recycler_view_lian_item_questions.adapter = adapter

                })

        }


    }

    var mediaPlayer: MediaPlayer? = null

    private fun plyDemoMp3Reading() {
        mediaPlayer =
            mediaPlayer ?: MediaPlayer.create(this, R.raw.demo_2018_national_test_vol2_section1)
        mediaPlayer?.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer?.stop()
    }

}