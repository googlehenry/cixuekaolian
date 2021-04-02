package com.viastub.kao100.module.xue

import android.media.MediaPlayer
import android.view.MotionEvent
import com.google.android.material.tabs.TabLayout
import com.viastub.kao100.R
import com.viastub.kao100.adapter.CommonViewPagerAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.base.BaseFragment
import kotlinx.android.synthetic.main.activity_xue_detail_page.*


class XuePage1Activity : BaseActivity() {

    var currentFragment: BaseFragment? = null

    override fun id(): Int {
        return R.layout.activity_xue_detail_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()

        project_tab_layout.setupWithViewPager(non_scrollable_view_pager)

        val commonViewPagerAdapter =
            CommonViewPagerAdapter(
                supportFragmentManager,
                mutableListOf("教材", "双语", "单词", "精解")
            ).apply {
                addFragment(XuePage1FragmentStudy())
                addFragment(XuePage1FragmentTranscript())
                addFragment(XuePage1FragmentWords())
                addFragment(XuePage1FragmentTeach())
            }

        val currentIdx = 0
        non_scrollable_view_pager.adapter = commonViewPagerAdapter
        non_scrollable_view_pager.currentItem = currentIdx
        non_scrollable_view_pager.offscreenPageLimit = 1
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

        play_pause.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                play_pause.setImageResource(R.drawable.player_icon_play)
                mediaPlayer?.pause()
            } else {
                play_pause.setImageResource(R.drawable.player_icon_pause)
                plyDemoMp3Reading()
            }
        }

    }

    var mediaPlayer: MediaPlayer? = null

    private fun plyDemoMp3Reading() {
        mediaPlayer =
            mediaPlayer ?: MediaPlayer.create(this, R.raw.demo_eng_textbook_reading_3_1_pep)
        mediaPlayer?.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return currentFragment?.let { if (it.touchEventAware()) it.onTouchEvent(event) else null }
            ?: super.onTouchEvent(event)
    }

    override fun onBackPressed() {
        mediaPlayer?.stop()
        super.onBackPressed()
    }

}