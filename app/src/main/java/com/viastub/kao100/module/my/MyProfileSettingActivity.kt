package com.viastub.kao100.module.my

import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ci_word_setting.*


class MyProfileSettingActivity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_my_profile_setting
    }

    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

    }


}