package com.qianli.cixuekaolian.module.xue

import android.view.MotionEvent
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.adapter.CommonViewPagerAdapter
import com.qianli.cixuekaolian.base.BaseActivity
import kotlinx.android.synthetic.main.activity_xue_page.*


class XuePage1Activity : BaseActivity() {
    var xuePage1FragmentStudy = XuePage1FragmentStudy()
    var xuePage1FragmentTeach = XuePage1FragmentTeach()

    override fun getLayoutId(): Int {
        return R.layout.activity_xue_page
    }

    override fun createPresenter() {

    }

    override fun initView() {
        project_tab_layout.setupWithViewPager(non_scrollable_view_pager)

        val commonViewPagerAdapter =
            CommonViewPagerAdapter(supportFragmentManager, mutableListOf("书本学习", "讲解分析")).apply {
                addFragment(xuePage1FragmentStudy)
                addFragment(xuePage1FragmentTeach)
            }

        non_scrollable_view_pager.adapter = commonViewPagerAdapter
        non_scrollable_view_pager.currentItem = 0
        non_scrollable_view_pager.isScrollble = false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return xuePage1FragmentStudy.onTouchEvent(event)
    }

}