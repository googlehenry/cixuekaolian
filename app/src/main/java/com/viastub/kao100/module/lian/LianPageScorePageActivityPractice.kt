package com.viastub.kao100.module.lian

import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lian_item_score_page.*

class LianPageScorePageActivityPractice : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_lian_item_score_page
    }


    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        intent?.let {
            var scoreEarned = it.getDoubleExtra("scoreEarned", 0.0)
            var rate = it.getIntExtra("rate", 0)
            var right = it.getIntExtra("right", 0)
            var wrong = it.getIntExtra("wrong", 0)
            var missing = it.getIntExtra("missing", 0)

            header_title.text = "测试结果页面"

            summary_exam_myScores.text = "得分:${scoreEarned}"

            summary_exam_details.text = "对:$right 错:$wrong 未答:$missing 正确率:${rate}%"
        }


    }

}