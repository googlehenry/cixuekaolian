package com.qianli.cixuekaolian.module.xue

import android.content.Intent
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.adapter.CommonViewPagerAdapter
import com.qianli.cixuekaolian.base.BaseActivity
import kotlinx.android.synthetic.main.activity_xue_chapter_page.*

class XuePage0Activity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_xue_chapter_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()

        project_tab_layout.setupWithViewPager(scrollable_view_pager)

        val commonViewPagerAdapter =
            CommonViewPagerAdapter(
                supportFragmentManager,
                mutableListOf("单元1", "单元2", "单元3", "单元4", "单元5", "单元6", "单元7", "单元8")
            ).apply {
                addFragment(XuePage0FragmentCover())
                addFragment(XuePage0FragmentCover())
                addFragment(XuePage0FragmentCover())
                addFragment(XuePage0FragmentCover())
                addFragment(XuePage0FragmentCover())
                addFragment(XuePage0FragmentCover())
                addFragment(XuePage0FragmentCover())
                addFragment(XuePage0FragmentCover())
            }

        val currentIdx = 0
        scrollable_view_pager.adapter = commonViewPagerAdapter
        scrollable_view_pager.currentItem = currentIdx

        header_back.setOnClickListener { onBackPressed() }

        btn_xue_start.setOnClickListener {
            startActivity(
                Intent(this, XuePage1Activity::class.java)
            )
        }
    }

}