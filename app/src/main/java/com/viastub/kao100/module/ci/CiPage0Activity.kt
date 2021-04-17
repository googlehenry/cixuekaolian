package com.viastub.kao100.module.ci

import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.utils.VariablesCi
import kotlinx.android.synthetic.main.activity_ci_word_detail_page.*
import kotlinx.coroutines.*
import java.util.*


class CiPage0Activity : BaseActivity(), TextToSpeech.OnInitListener {
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

                        var findLinks =
                            VariablesCi.ciContext?.wordKeys?.filter { it == word }
                                ?: mutableListOf()
                        while (word.length > 0 && findLinks.isNullOrEmpty()) {
                            word = word.substring(0, word.length - 1)
                            findLinks =
                                VariablesCi.ciContext?.wordKeys?.filter { it == word }
                                    ?: mutableListOf()
                        }

                        if (findLinks.size == 1) {
                            loadLinkedWord(word)
                        } else {
                            Toast.makeText(this@CiPage0Activity, "没有合适的链接", Toast.LENGTH_SHORT)
                                .show()
                        }
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
            } else {
                header_status.text = "手动模式"
            }

            CoroutineScope(Dispatchers.IO).launch {

                if (VariablesCi.autoTimer == null) {

                    VariablesCi.autoTimer = Timer()
                    VariablesCi.autoTimer!!.scheduleAtFixedRate(
                        object : TimerTask() {
                            override fun run() {
                                if (VariablesCi.ciContext!!.currentIndex >= VariablesCi.ciContext!!.currentWordList!!.size - 1) {
                                    VariablesCi.autoTimer?.cancel()
                                    VariablesCi.autoTimer = null
                                } else {
                                    runOnUiThread {
                                        gotoWordFromDict(1)
                                    }
                                }
                            }

                        },
                        1000,
                        VariablesCi.ciContext!!.dictConfig!!.autoNextIntervalSeconds * 1000.toLong()
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

    }


    var networkMap: MutableMap<String, Int> = mutableMapOf()

    private fun playSoundOfCurrentWord() {
        var playedOnlineSound = false
        //step1: prefer to play online sound
        VariablesCi.ciContext?.dictConfig?.onlineSpeakingLinkTemplate?.let { template ->
            if (template.isNotBlank() && (networkMap[template] ?: 0) < 4) {
                VariablesCi.ciContext!!.currentword?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            withTimeout(5000) {
                                var mediaPlayer = MediaPlayer()
                                mediaPlayer.setDataSource(template.replace("#word", it))
                                mediaPlayer.prepare()
                                mediaPlayer.start()
                                playedOnlineSound = true
                            }
                        } catch (ex: TimeoutCancellationException) {
                            networkMap[template] = (networkMap[template] ?: 0) + 1
                        }
                    }
                }
            }
        }

        //step2: fallback, Play TTS if enabled and no online configed
        if (!playedOnlineSound && VariablesCi.ciContext?.dictConfig?.ttsEnabled == true) {
            VariablesCi.ciContext!!.currentword?.let {
                speech!!.speak(it, TextToSpeech.QUEUE_FLUSH, null, "oops")
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
            );

        }

        //after all set activities, playSoundAtStart
        VariablesCi.ciContext?.dictConfig?.let {
            if (it.playSoundAtStart) {
                playSoundOfCurrentWord()
            }
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

        if (rootWordLinks.size == 0 && index == 0) {
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

}