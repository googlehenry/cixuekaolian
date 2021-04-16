package com.viastub.kao100.module.xue

import android.media.MediaPlayer
import android.os.CountDownTimer
import android.view.MotionEvent
import android.widget.SeekBar
import androidx.core.net.toUri
import com.google.android.material.tabs.TabLayout
import com.viastub.kao100.R
import com.viastub.kao100.adapter.CommonViewPagerAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.beans.XueContext
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.VariablesXue
import kotlinx.android.synthetic.main.activity_xue_detail_page.*
import java.io.File


class XuePage1Activity : BaseActivity() {
    var currentFragment: BaseFragment? = null
    var currentPlayAudioIndex = -1

    override fun id(): Int {
        return R.layout.activity_xue_detail_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()

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
                        mutableListOf("教材", "英语", "中文", "单词", "精解")
                    ).apply {
                        addFragment(XuePage1FragmentStudy(it.pageSnapshotPaths()))
                        addFragment(XuePage1FragmentTrans(it.bookTranslationsDb, true))
                        addFragment(XuePage1FragmentTrans(it.bookTranslationsDb, false))
                        addFragment(XuePage1FragmentWords(it.bookWordItemsDb))
                        addFragment(XuePage1FragmentTeach(it.bookTeachingPointsDb))
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
                        currentFragment!!.onResume()
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {}
                    override fun onTabReselected(tab: TabLayout.Tab?) {}
                })

                it.audioPaths()?.firstOrNull()?.let {
                    currentPlayAudioIndex = 0
                    var audioFileInit = File(it)
                    player_current_item_name.text = "当前音频:${audioFileInit.name}"
                    mediaPlayer = MediaPlayer.create(this, audioFileInit.toUri())
                    mediaPlayer?.setVolume(1.0f, 1.0f)
                }

            })
        }


        header_back.setOnClickListener { onBackPressed() }

        play_pause.setOnClickListener {
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

    }

    private fun playAnother(step: Int) {
        playerTimer?.cancel()
        VariablesXue.xueContext?.unit?.audioPaths()?.let {
            currentPlayAudioIndex += step
            if (currentPlayAudioIndex >= 0 && currentPlayAudioIndex < it.size) {
                mediaPlayer?.let {
                    it.stop()
                    play_pause.setImageResource(R.drawable.player_icon_pause)
                }
                var availableNextAudio = File(it[currentPlayAudioIndex])

                mediaPlayer = MediaPlayer.create(this, availableNextAudio.toUri())
                mediaPlayer?.setVolume(1.0f, 1.0f)
                mediaPlayer?.start()
                player_current_item_name.text = "当前音频:${availableNextAudio.name}"

                setPlayerUpTimer()
                playerTimer?.start()
            }
        }

    }

    var playerTimer: CountDownTimer? = null
    fun setPlayerUpTimer() {
        mediaPlayer?.let {
            if (it.duration > 0) {
                var countDownTimer = object : CountDownTimer(it.duration.toLong(), 500) {
                    override fun onTick(millisUntilFinished: Long) {
                        player_seekbar.max = (it.duration / 1000)
                        player_seekbar.progress = (it.currentPosition / 1000)
                        play_startText.text = formatTimeToMinutes(it.currentPosition)
                        play_endText.text = formatTimeToMinutes(it.duration)
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
        mediaPlayer?.stop()
        super.onBackPressed()
    }

}