package com.viastub.kao100.module.xue

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.net.toUri
import com.google.android.material.tabs.TabLayout
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton
import com.viastub.kao100.R
import com.viastub.kao100.adapter.CommonViewPagerAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.beans.XueContext
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.module.my.MyCiHistoryPageActivity
import com.viastub.kao100.module.my.MyCollectionHistoryPageActivity
import com.viastub.kao100.module.my.MyPracticeHistoryPageActivity
import com.viastub.kao100.utils.VariablesXue
import com.yechaoa.yutilskt.LogUtilKt
import kotlinx.android.synthetic.main.activity_kao_exam_summary.*
import kotlinx.android.synthetic.main.activity_xue_chapter_page.*
import kotlinx.android.synthetic.main.activity_xue_detail_page.*
import kotlinx.android.synthetic.main.activity_xue_detail_page.floating_buttons_menus
import kotlinx.android.synthetic.main.activity_xue_detail_page.header_back
import kotlinx.android.synthetic.main.activity_xue_detail_page.project_tab_layout
import java.io.File


class XuePage1Activity : BaseActivity(), ProgressUpdatedListener {
    var currentFragment: BaseFragment? = null
    var currentPlayAudioIndex = -1

    override fun id(): Int {
        return R.layout.activity_xue_detail_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }
        project_tab_layout.setupWithViewPager(non_scrollable_view_pager)

        var xueContext = intent?.extras?.get("xueContext")?.let { it as XueContext }
        VariablesXue.xueContext = xueContext

        var unit = xueContext?.unit

        unit?.let {
            awaitAsync({
                var roomDb = RoomDB.get(applicationContext)
                it.bookWordItemsDb =
                    it.bookWordItemIds()?.let { roomDb.teachingBookWord().getByIds(it) }
                        ?.toMutableList()
                it.bookTranslationsDb = it.teachingBookTranslationsIds()
                    ?.let { roomDb.teachingTranslation().getByIds(it) }?.toMutableList()
                it.bookTeachingPointsDb =
                    it.bookTeachingPointIds()?.let { roomDb.teachingPoint().getByIds(it) }
                        ?.toMutableList()
                it
            }, {
                title_high.text = "${unit.name} ${unit.description}"

                val commonViewPagerAdapter =
                    CommonViewPagerAdapter(
                        supportFragmentManager,
                        mutableListOf("教材", "中文", "英文", "精解", "单词")
                    ).apply {
                        addFragment(
                            XuePage1FragmentStudy(
                                this@XuePage1Activity,
                                it.pageSnapshotPaths()
                            )
                        )
                        addFragment(
                            XuePage1FragmentTrans(
                                this@XuePage1Activity,
                                it.bookTranslationsDb,
                                false
                            )
                        )
                        addFragment(
                            XuePage1FragmentTrans(
                                this@XuePage1Activity,
                                it.bookTranslationsDb,
                                true
                            )
                        )
                        addFragment(XuePage1FragmentTeach(it.bookTeachingPointsDb))
                        addFragment(XuePage1FragmentWords(it.bookWordItemsDb))
                    }

                val currentIdx = 0
                non_scrollable_view_pager.adapter = commonViewPagerAdapter
                non_scrollable_view_pager.currentItem = currentIdx
                non_scrollable_view_pager.offscreenPageLimit = 5
                currentFragment = commonViewPagerAdapter.getItem(currentIdx) as BaseFragment

                project_tab_layout.addOnTabSelectedListener(object :
                    TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        currentFragment =
                            commonViewPagerAdapter.getItem(tab!!.position) as BaseFragment

                        when (currentFragment) {
                            is XuePage1FragmentStudy -> (currentFragment as XuePage1FragmentStudy).loadPage(
                                VariablesXue.xueContext?.currentPageIndex!!
                            )
                            is XuePage1FragmentTrans -> (currentFragment as XuePage1FragmentTrans).loadPage(
                                VariablesXue.xueContext?.currentPageIndex!!
                            )
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {}
                    override fun onTabReselected(tab: TabLayout.Tab?) {}
                })

                it.audioPaths()?.firstOrNull()?.let {
                    currentPlayAudioIndex = 0

                    playAnother(0)
                }

            })
        }
        player_menu.setOnClickListener {
            Toast.makeText(
                this,
                "当前播放:$currentAudioName",
                Toast.LENGTH_LONG
            ).show()
        }

        play_pause.setOnClickListener {
            if (mediaPlayer == null) {
                playAnother(0)
            } else {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        play_pause.setImageResource(R.drawable.player_icon_play)
                        it.pause()
                        playerTimer?.cancel()
                    } else {
                        play_pause.setImageResource(R.drawable.player_icon_pause)
                        it.start()
                        setPlayerUpTimer()
                        playerTimer?.start()
                    }
                }
            }

        }

        play_prev.setOnClickListener {
            playAnother(-1)
        }
        play_next.setOnClickListener {
            playAnother(1)
        }

        player_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekbar: SeekBar?,
                current: Int,
                changedByUser: Boolean
            ) {//如果插件的值变了
                if (changedByUser) {
                    mediaPlayer?.let {
                        it.seekTo(current * 1000)
                    }
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {//刚开始触摸的时候
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {//结束触摸
            }

        })

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

    var currentAudioName: String? = null
    private fun playAnother(step: Int) {

        currentPlayAudioIndex += step
        playerTimer?.cancel()
        VariablesXue.xueContext?.unit?.audioPaths()?.let {

            if (currentPlayAudioIndex >= 0 && currentPlayAudioIndex < it.size) {
                var availableNextAudio = File(it[currentPlayAudioIndex])
                currentAudioName = availableNextAudio.name

                mediaPlayer?.let {
                    it.stop()
                    play_pause.setImageResource(R.drawable.player_icon_play)
                } ?: play_pause.setImageResource(R.drawable.player_icon_pause)

                mediaPlayer = MediaPlayer.create(this, availableNextAudio.toUri())
                mediaPlayer?.setVolume(1.0f, 1.0f)
                mediaPlayer?.start()

                setPlayerUpTimer()
                playerTimer?.start()
            } else {
                if (currentPlayAudioIndex < 0) {
                    currentPlayAudioIndex = 0
                } else {
                    currentPlayAudioIndex = it.size - 1
                }
            }
        }

    }

    var playerTimer: CountDownTimer? = null
    fun setPlayerUpTimer() {
        mediaPlayer?.let {
            if (it.duration > 0) {
                var countDownTimer = object : CountDownTimer(it.duration.toLong(), 500) {
                    override fun onTick(millisUntilFinished: Long) {
                        try {
                            player_seekbar.max = (it.duration / 1000)
                            player_seekbar.progress = (it.currentPosition / 1000)
                            play_startText.text = formatTimeToMinutes(it.currentPosition)
                            play_endText.text = formatTimeToMinutes(it.duration)
                        } catch (ex: IllegalStateException) {
                            ex.message?.let { LogUtilKt.e(it) }
                        }
                    }

                    override fun onFinish() {
                    }

                }
                playerTimer = countDownTimer
            }


        }

    }

    fun formatTimeToMinutes(miliseconds: Int): String {
        var minutes = miliseconds / 60000
        var seconds = (miliseconds % 60000) / 1000
        return "${if (minutes < 10) "0" + minutes else minutes}:${if (seconds < 10) "0" + seconds else seconds}"
    }

    var mediaPlayer: MediaPlayer? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return currentFragment?.let { if (it.touchEventAware()) it.onTouchEvent(event) else null }
            ?: super.onTouchEvent(event)
    }

    //player_seekbar
    override fun onBackPressed() {
        super.onBackPressed()
        stopPlayer()
    }

    override fun updateProgress(secondary: Int, primary: Int, max: Int) {
        teaching_book_unit_progress.max = max
        teaching_book_unit_progress.progress = primary
        teaching_book_unit_progress.secondaryProgress = secondary
    }

    override fun onPause() {
        super.onPause()
        stopPlayer()
    }

    private fun stopPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

interface ProgressUpdatedListener {
    fun updateProgress(secondary: Int, primary: Int, max: Int)
}