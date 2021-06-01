package com.viastub.kao100.module.xue

import android.content.Intent
import android.net.Uri
import android.view.View
import com.viastub.kao100.R
import com.viastub.kao100.adapter.CommonViewPagerAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.beans.XueContext
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.db.TeachingBook
import com.viastub.kao100.module.my.MyCiHistoryPageActivity
import com.viastub.kao100.module.my.MyCollectionHistoryPageActivity
import com.viastub.kao100.module.my.MyPracticeHistoryPageActivity
import com.yechaoa.yutilskt.LogUtilKt
import kotlinx.android.synthetic.main.activity_xue_chapter_page.*

class XuePage0Activity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_xue_chapter_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()

        var teachingBook = intent?.extras?.get("teachingBook")?.let { it as TeachingBook }
        project_tab_layout.setupWithViewPager(scrollable_view_pager)
        header_back.setOnClickListener { onBackPressed() }


        teachingBook?.coverImage()?.let {
            if (!it.exists()) {
                LogUtilKt.i("Path not existed:$it")
            }
            textbook_coverImage.setImageURI(Uri.fromFile(it))
        }
        textbook_name.text = teachingBook?.name

        sidebar_action_mywords.setOnClickListener {
            var intent = Intent(this, MyCiHistoryPageActivity::class.java)
            startActivity(intent)
        }
        sidebar_action_myquestions.setOnClickListener {
            var intent = Intent(this, MyPracticeHistoryPageActivity::class.java)
            startActivity(intent)
        }
        sidebar_action_mynotes.setOnClickListener {
            var intent = Intent(this, MyCollectionHistoryPageActivity::class.java)
            startActivity(intent)
        }
        sidebar_action_hideme.setOnClickListener {
            sidebar_action_holder.visibility = View.GONE
        }

        teachingBook?.unitIds()?.let {
            awaitAsync({
                var roomDb = RoomDB.get(applicationContext)
                roomDb.teachingBookUnitSection().getByIds(it)?.toMutableList() ?: mutableListOf()
            }, { units ->
                textbook_chapters_number.text = "章节数量:${units.size}"
                val commonViewPagerAdapter =
                    CommonViewPagerAdapter(
                        supportFragmentManager, units.map { it.name }.toMutableList()
                    ).apply {
                        units.forEach {
                            addFragment(XuePage0FragmentCover(it))
                        }
                    }

                scrollable_view_pager.adapter = commonViewPagerAdapter
                scrollable_view_pager.currentItem = 0
                scrollable_view_pager.offscreenPageLimit = 3

                btn_xue_start.setOnClickListener {
                    var unit = units[scrollable_view_pager.currentItem]

                    var intent = Intent(this, XuePage1Activity::class.java)

                    intent.putExtra("xueContext", XueContext(unit))

                    startActivity(intent)
                }
            })
        }


    }

}