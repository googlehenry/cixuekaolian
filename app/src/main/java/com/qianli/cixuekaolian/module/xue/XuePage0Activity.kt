package com.qianli.cixuekaolian.module.xue

import android.content.Intent
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.base.BaseActivity
import kotlinx.android.synthetic.main.activity_xue_chapter_page.*

class XuePage0Activity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_xue_chapter_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()

        btn_xue_start.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    XuePage1Activity::class.java
                )
            )
        }
    }

}