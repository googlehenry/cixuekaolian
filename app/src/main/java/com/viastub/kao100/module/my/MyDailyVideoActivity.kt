package com.viastub.kao100.module.my

import android.view.View
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.MyInspiration
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.http.RemoteAPIDataService
import kotlinx.android.synthetic.main.activity_ci_word_setting.header_back
import kotlinx.android.synthetic.main.activity_my_video.*


class MyDailyVideoActivity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_my_video
    }

    var videoPath: String? = null
    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        video_player.setOnClickListener {
            videoPath?.let {
                if (video_player.isPlaying) {
                    video_player.pause()
                    video_control_paused.visibility = View.VISIBLE
                } else {
                    video_player.start()
                    video_control_paused.visibility = View.GONE
                }
            }
        }

        doAsync {
            RemoteAPIDataService.apis.getInspirations().onErrorReturn {
                mutableListOf<MyInspiration>()
            }.subscribe { resp ->
                var roomDB = RoomDB.get(applicationContext)
                if (!resp.isNullOrEmpty()) {
                    resp.sortedBy { it.id }
                    roomDB.myInspiration().insert(resp.toMutableList())

                    var latest = resp.last()

                    runOnUiThread {
                        playVideoInspiration(latest)
                    }
                } else {
                    var latest = roomDB.myInspiration().getLatest()
                    latest?.let {
                        runOnUiThread {
                            playVideoInspiration(latest)
                        }
                    }

                }
            }
        }

    }

    private fun playVideoInspiration(latest: MyInspiration) {
        if (!latest.videoLink.isNullOrBlank()) {
            videoPath = latest.videoLink
            video_title.text = latest.videoTitle
            video_desc.text = latest.videoDesc
            if (!latest.videoImageCoverLink.isNullOrBlank()) {
                video_cover_image_net.setImageURL(latest.videoImageCoverLink)
            }
            if (!latest.motto.isNullOrBlank()) {
                video_motto.text = latest.motto
            }
            video_player.setVideoPath(latest.videoLink)
            video_player.start()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        video_player?.stopPlayback()
    }

}