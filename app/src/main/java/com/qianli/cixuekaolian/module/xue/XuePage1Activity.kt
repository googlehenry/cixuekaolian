package com.qianli.cixuekaolian.module.xue

import android.view.MotionEvent
import com.google.android.material.tabs.TabLayout
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.adapter.CommonViewPagerAdapter
import com.qianli.cixuekaolian.base.BaseActivity
import com.qianli.cixuekaolian.base.BaseFragment
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.activity_xue_page.*


class XuePage1Activity : BaseActivity() {

    var currentFragment: BaseFragment? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_xue_page
    }

    override fun createPresenter() {

    }

    override fun initView() {
        supportActionBar?.hide()

        project_tab_layout.setupWithViewPager(non_scrollable_view_pager)

        val commonViewPagerAdapter =
            CommonViewPagerAdapter(supportFragmentManager, mutableListOf("书本学习", "讲解分析")).apply {
                addFragment(XuePage1FragmentStudy())
                addFragment(XuePage1FragmentTeach())
            }

        val currentIdx = 0
        non_scrollable_view_pager.adapter = commonViewPagerAdapter
        non_scrollable_view_pager.currentItem = currentIdx
        currentFragment = commonViewPagerAdapter.getItem(currentIdx) as BaseFragment

        project_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentFragment = commonViewPagerAdapter.getItem(tab!!.position) as BaseFragment
                non_scrollable_view_pager.isScrollble =
                    !(currentFragment?.touchEventAware() == true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        header_back.setOnClickListener { onBackPressed() }
        header_action.setOnClickListener { ToastUtilKt.showCenterToast("去换章节页面") }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return currentFragment?.let { if (it.touchEventAware()) it.onTouchEvent(event) else null }
            ?: super.onTouchEvent(event)
    }

}