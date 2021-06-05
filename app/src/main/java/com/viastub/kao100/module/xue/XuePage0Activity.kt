package com.viastub.kao100.module.xue

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton
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

                    if (units.isNotEmpty()) {
                        var unit = units[scrollable_view_pager.currentItem]

                        var intent = Intent(this, XuePage1Activity::class.java)

                        intent.putExtra("xueContext", XueContext(unit))

                        startActivity(intent)
                    }
                }
            })
        }

        floatingButtonMenusSetup()
    }

    private fun floatingButtonMenusSetup() {

        val itemBuilder = SubActionButton.Builder(this)
        itemBuilder.setBackgroundDrawable(
            BitmapDrawable(
                resources,
                BitmapFactory.decodeResource(resources, R.drawable.shape_button_round_white)
            )
        )

        val actionMenu = FloatingActionMenu.Builder(this)
            .addSubActionView(
                itemBuilder
                    .setContentView(ImageView(this).also {
                        it.isClickable = true
                        it.imageTintList = resources.getColorStateList(R.color.colorPrimary, null)
                        it.setImageResource(R.drawable.ic_func_dictionary)
                        it.setOnTouchListener { view: View, motionEvent: MotionEvent ->
                            when (motionEvent.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    startActivity(
                                        Intent(
                                            this,
                                            MyCiHistoryPageActivity::class.java
                                        )
                                    )
                                }
                            }
                            false
                        }
                    }).build()
            )
            .addSubActionView(itemBuilder.setContentView(ImageView(this).also {
                it.isClickable = true
                it.imageTintList = resources.getColorStateList(R.color.colorPrimary, null)
                it.setImageResource(R.drawable.ic_func_textbook)
                it.setOnTouchListener { view: View, motionEvent: MotionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startActivity(
                                Intent(
                                    this,
                                    MyCollectionHistoryPageActivity::class.java
                                )
                            )
                        }
                    }
                    false
                }
            }).build())
            .addSubActionView(itemBuilder.setContentView(ImageView(this).also {
                it.isClickable = true
                it.imageTintList = resources.getColorStateList(R.color.colorPrimary, null)
                it.setImageResource(R.drawable.ic_func_practice)
                it.setOnTouchListener { view: View, motionEvent: MotionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startActivity(
                                Intent(
                                    this,
                                    MyPracticeHistoryPageActivity::class.java
                                )
                            )
                        }
                    }
                    false
                }
            }).build())
            .addSubActionView(
                itemBuilder
                    .setContentView(ImageView(this).also {
                        it.isClickable = true
                        it.imageTintList = resources.getColorStateList(R.color.colorPrimary, null)
                        it.setImageResource(R.drawable.icon_button_plus)

                        it.setOnTouchListener { view: View, motionEvent: MotionEvent ->
                            when (motionEvent.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    val cm: ClipboardManager? =
                                        getSystemService(Context.CLIPBOARD_SERVICE)
                                            ?.let { it as ClipboardManager }

                                    var txt = cm!!.primaryClip?.getItemAt(0)?.text.toString()
                                    addNewCollectDialog(txt, "试卷简介,手动添加")
                                }
                            }
                            false
                        }
                    }).build()
            )
            .setStartAngle(180)
            .setEndAngle(270)
            .attachTo(floating_buttons_menus)
            .build()

    }

}