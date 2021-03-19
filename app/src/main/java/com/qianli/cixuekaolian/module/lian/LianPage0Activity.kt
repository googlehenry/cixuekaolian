package com.qianli.cixuekaolian.module.lian

import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.base.BaseActivity

class LianPage0Activity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_lian_item_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()
    }

}