package com.viastub.kao100.module.ci

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.MyWordHistory
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.Variables
import com.viastub.kao100.utils.VariablesCi
import com.viastub.kao100.wigets.OnQueryWordRequestedListener
import kotlinx.android.synthetic.main.activity_ci_word_detail_page.*
import kotlinx.android.synthetic.main.activity_ci_word_detail_page.header_back
import kotlinx.android.synthetic.main.activity_lian_item_page.*
import kotlinx.coroutines.*
import java.util.*


class CiPage0Activity : BaseActivity(), TextToSpeech.OnInitListener, OnQueryWordRequestedListener {
    var speech: TextToSpeech? = null

    override fun id(): Int {
        return R.layout.activity_ci_word_detail_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }
        doAsync {
            setUpSpeeachTTS()
        }
        var wordsx = intent?.extras?.get("wordlist")?.let { it as ArrayList<String> }
        wordsx?.let {
            VariablesCi.ciContext!!.currentWordList = it.toMutableList()
        }

        ci_word_detail.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                //entry://workout
                request?.url?.let {
                    var link = it.toString().toLowerCase()
                    if (link.startsWith("entry://")) {
                        var word = link.replace("entry://", "")

                        goToLinkInWebview(word)
                    }
                }

                return true
            }
        }

        if (VariablesCi.autoTimer == null) {
            header_status.text = "手动模式"
        } else {
            header_status.text =
                "自动模式[${VariablesCi.ciContext?.dictConfig?.autoNextIntervalSeconds}s]"
        }


        gotoWordFromDict(0)

        action_settings.setOnClickListener {
            startActivity(Intent(this, CiPage1SettingActivity::class.java))
        }

        action_autoNext.setOnClickListener {
            Toast.makeText(this, "切换模式", Toast.LENGTH_SHORT).show()

            if (VariablesCi.autoTimer == null) {
                header_status.text =
                    "自动模式[${VariablesCi.ciContext?.dictConfig?.autoNextIntervalSeconds}s]"
                action_autoNext.setBackgroundResource(R.drawable.ci_word_timer_off)
            } else {
                header_status.text = "手动模式"
                action_autoNext.setBackgroundResource(R.drawable.ci_word_timer_on)
            }

            CoroutineScope(Dispatchers.IO).launch {

                if (VariablesCi.autoTimer == null) {
                    VariablesCi.autoTimer = Timer()
                    val interval =
                        VariablesCi.ciContext!!.dictConfig!!.autoNextIntervalSeconds * 1000.toLong()
                    var countDownTimer: CountDownTimer? = null
                    VariablesCi.autoTimer!!.scheduleAtFixedRate(
                        object : TimerTask() {
                            override fun run() {
                                if (VariablesCi.ciContext!!.currentIndex >= VariablesCi.ciContext!!.currentWordList!!.size - 1) {
                                    VariablesCi.autoTimer?.cancel()
                                    VariablesCi.autoTimer = null
                                } else {
                                    runOnUiThread {
                                        gotoWordFromDict(1)
                                        countDownTimer?.let { it.cancel() }
                                        countDownTimer = setUpTimer(interval)
                                        countDownTimer?.start()
                                    }
                                }
                            }

                        },
                        1000,
                        interval
                    )
                } else {
                    VariablesCi.autoTimer?.cancel()
                    VariablesCi.autoTimer = null

                }
            }
        }

        action_speak.setOnClickListener {
            playSoundOfCurrentWord()
            Toast.makeText(this, "播放读音", Toast.LENGTH_SHORT).show()
        }

        action_next.setOnClickListener {
            gotoWordFromDict(1)
            Toast.makeText(this, "下一个", Toast.LENGTH_SHORT).show()
        }

        action_prev.setOnClickListener {
            //If user clicks links, first go back to last link, then go to previous word in list
            var list = VariablesCi.ciContext?.currentWordList
            var index = VariablesCi.ciContext?.currentIndex ?: 0
            var rootWord = list?.get(index)!!
            var rootWordLinks: LinkedList<String> =
                VariablesCi.ciContext?.currentWordRootLinks?.get(rootWord) ?: LinkedList()

            var linksize = rootWordLinks.size
            if (linksize > 0) {
                rootWordLinks.removeLast()
            }
            VariablesCi.ciContext?.currentWordRootLinks?.put(rootWord, rootWordLinks)

            if (rootWordLinks.size > 0) {
                var word = rootWordLinks.last
                loadword(word, rootWordLinks.size)
            } else if (rootWordLinks.size == 0) {
                if (linksize == 0) {
                    gotoWordFromDict(-1) //move prev in list
                } else {
                    gotoWordFromDict(0) // show current root word
                }
            } else {
                gotoWordFromDict(-1)
            }

            Toast.makeText(this, "上一个", Toast.LENGTH_SHORT).show()
        }

        ci_word_detail.queryWordListener = this
    }

    private fun goToLinkInWebview(word: String) {
        var word1 = word
        var findLinks =
            VariablesCi.ciContext?.wordKeys?.filter { it == word1 }
                ?: mutableListOf()
        while (word1.length > 0 && findLinks.isNullOrEmpty()) {
            word1 = word1.substring(0, word1.length - 1)
            findLinks =
                VariablesCi.ciContext?.wordKeys?.filter { it == word1 }
                    ?: mutableListOf()
        }

        if (findLinks.size == 1) {
            loadLinkedWord(word1)
        } else {
            Toast.makeText(this@CiPage0Activity, "没有合适的链接", Toast.LENGTH_SHORT)
                .show()
        }
    }


    fun setUpTimer(milliSeconds: Long): CountDownTimer {

        var countDownTimer = object : CountDownTimer(milliSeconds, 500) {
            override fun onTick(millisUntilFinished: Long) {
                if (VariablesCi.autoTimer != null) {
                    var seconds = millisUntilFinished / 1000
                    header_status.text =
                        "自动模式[${if (seconds < 10) "0" + seconds else seconds}]"
                }
            }

            override fun onFinish() {

            }

        }
        return countDownTimer
    }


    var networkMap: MutableMap<String, Int> = mutableMapOf()
    private fun playSoundOfCurrentWord() {
        var playedOnlineSound = false

        CoroutineScope(Dispatchers.IO).launch {
            //step1: prefer to play online sound
            supervisorScope {

                launch {
                    VariablesCi.ciContext?.dictConfig?.onlineSpeakingLinkTemplate?.let { template ->
                        if (template.isNotBlank() && (networkMap[template] ?: 0) < 3) {
                            VariablesCi.ciContext!!.currentword?.let {

                                try {
                                    var mediaPlayer = MediaPlayer()
                                    mediaPlayer.setDataSource(template.replace("#word", it))
                                    mediaPlayer.setOnErrorListener { mp, what, extra ->
                                        networkMap[template] = (networkMap[template] ?: 0) + 1
                                        mp.release()
                                        true
                                    }
                                    mediaPlayer.setOnCompletionListener {
                                        it.release()
                                    }
                                    mediaPlayer.prepare()
                                    mediaPlayer.start()
                                    playedOnlineSound = true
                                } catch (ex: Exception) {
                                    networkMap[template] = (networkMap[template] ?: 0) + 1
                                }

                            }
                        }
                    }
                }
                launch {
                    val template = VariablesCi.ciContext?.dictConfig?.onlineSpeakingLinkTemplate
                    if (template != null && (networkMap[template] ?: 0) < 3) {
                        delay(2000)
                    }
                    //step2: fallback, Play TTS if enabled and no online configed
                    if (!playedOnlineSound && VariablesCi.ciContext?.dictConfig?.ttsEnabled == true) {
                        VariablesCi.ciContext!!.currentword?.let {
                            runOnUiThread {
                                speech!!.speak(it, TextToSpeech.QUEUE_FLUSH, null, "oops")
                            }
                        }
                    }
                }
            }

        }


    }

    private fun loadLinkedWord(word: String) {
        var list = VariablesCi.ciContext?.currentWordList
        var index = VariablesCi.ciContext?.currentIndex ?: 0
        var rootWord = list?.get(index)!!
        var rootWordLinks: LinkedList<String> =
            VariablesCi.ciContext?.currentWordRootLinks?.get(rootWord)
                ?: LinkedList()
        rootWordLinks.add(word)

        loadword(word, rootWordLinks.size)


        VariablesCi.ciContext?.currentWordRootLinks?.put(
            rootWord,
            rootWordLinks
        )
    }

    private fun setUpSpeeachTTS() {
        speech = TextToSpeech(this, this)
    }

    private fun gotoWordFromDict(step: Int = 0) {
        var list = VariablesCi.ciContext?.currentWordList
        var index = VariablesCi.ciContext?.currentIndex ?: 0
        index += step


        list?.let {
            if (it.isNotEmpty()) {
                var wd = if (index < it.size && index >= 0) list[index] else null
                wd?.let {
                    VariablesCi.ciContext?.currentIndex = index
                    loadword(wd)
                }
                if (wd == null) {
                    Toast.makeText(this, "已经到底了", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun loadword(wd: String, links: Int = 0) {
        var list = VariablesCi.ciContext?.currentWordList
        var index = VariablesCi.ciContext?.currentIndex ?: 0

        if (links > 0) {
            title_high.text = "单词:${list?.get(index)}..${wd}"
            VariablesCi.ciContext!!.currentword = wd
//            speech?.speak(wd, TextToSpeech.QUEUE_FLUSH, null, "oops")
        } else {
            var wd = list?.get(index)
            title_high.text = "单词:${wd} (${index + 1}/${list?.size})"
            VariablesCi.ciContext!!.currentword = wd
//            speech?.speak(wd, TextToSpeech.QUEUE_FLUSH, null, "oops")
        }

        word_list_dict_progress.max = list?.size!!
        word_list_dict_progress.secondaryProgress = index + 1


        VariablesCi.ciContext?.dictConfig?.mdict?.let {
            var explanation = it.getRecordAt(it.lookUp(wd))

            ci_word_detail.loadDataWithBaseURL(
                "file:///android_asset/www/",
                getHtmlData(explanation),
                "text/html",
                "UTF-8",
                null
            )
        }

        //after all set activities, playSoundAtStart
        VariablesCi.ciContext?.dictConfig?.let {
            if (it.playSoundAtStart) {
                playSoundOfCurrentWord()
            }
        }

        doAsync {
            val roomDb = RoomDB.get(applicationContext)
            var myWordHistory = roomDb.myWordHistory()
                .getByUserIdAndWord(Variables.currentUserId, VariablesCi.ciContext!!.currentword!!)
                ?: MyWordHistory(
                    userId = Variables.currentUserId,
                    word = VariablesCi.ciContext!!.currentword!!
                )

            myWordHistory.visitCount += 1
            roomDb.myWordHistory().insert(myWordHistory)

        }
    }

    private fun getHtmlData(data: String): String {
        val head =
            """
                <head>
                    <style>
                    @font-face{
                        font-family:'Kingsoft Phonetic Plain';
                        src:url('Ksphonet.ttf')
                    }
                    </style>
                </head>
            """.trimIndent()
        return "<html>$head<body style='background:#CCE8CF;'>$data</body></html>"
    }

    override fun onBackPressed() {

        var list = VariablesCi.ciContext?.currentWordList
        var index = VariablesCi.ciContext?.currentIndex ?: 0
        var rootWord = list?.get(index)!!
        var rootWordLinks: LinkedList<String> =
            VariablesCi.ciContext?.currentWordRootLinks?.get(rootWord) ?: LinkedList()

        if (rootWordLinks.size == 0 && index == (VariablesCi.ciContext?.initIndex ?: 0)) {
            super.onBackPressed()
            speech?.stop()
            speech?.shutdown()
            VariablesCi.autoTimer?.cancel()
            VariablesCi.autoTimer = null
        } else {
            val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
            dialog.setTitle("还未学完单词,仍然退出?")
            dialog.setPositiveButton("退出") { dialog, which ->
                super.onBackPressed()
                speech?.stop()
                speech?.shutdown()
                VariablesCi.autoTimer?.cancel()
                VariablesCi.autoTimer = null
            }
            dialog.setNegativeButton("不退出") { dialog, which -> dialog?.dismiss() }
            dialog.show()
        }


    }

    override fun onInit(status: Int) {
        if (status === TextToSpeech.SUCCESS) {
            //int result = mSpeech.setLanguage(Locale.ENGLISH);
            speech?.language = Locale.ENGLISH
            speech?.setPitch(0.75F)
//            speech?.setSpeechRate(0.75f)
//            VariablesCi.ciContext!!.currentword?.let {
//                speech!!.speak("gud", TextToSpeech.QUEUE_FLUSH, null, "oops")
//            }
        } else {
            Toast.makeText(this, "手机不支持合成语音", Toast.LENGTH_SHORT).show()
        }
    }

    override fun query(wordList: List<String>, word: String) {
        runOnUiThread {
            if (wordList.size > 1) {
                val startIdx = wordList.indexOf(word)
                VariablesCi.ciContext!!.currentWordList = wordList.toMutableList()
                VariablesCi.ciContext!!.currentIndex = startIdx
                VariablesCi.ciContext!!.initIndex = startIdx
                VariablesCi.ciContext!!.currentWordRootLinks = mutableMapOf()
                gotoWordFromDict(0)
            } else {
                goToLinkInWebview(word)
            }
        }

    }

}