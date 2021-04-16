package com.viastub.kao100.module.ci

import android.app.AlertDialog
import android.speech.tts.TextToSpeech
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.utils.VariablesCi
import kotlinx.android.synthetic.main.activity_ci_word_detail_page.*
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
                    if (it.toString().startsWith("entry://")) {
                        var word = it.toString().replace("entry://", "")

                        var list = VariablesCi.ciContext?.currentWordList
                        var index = VariablesCi.ciContext?.currentIndex ?: 0
                        var rootWord = list?.get(index)!!
                        var rootWordLinks: LinkedList<String> =
                            VariablesCi.ciContext?.currentWordRootLinks?.get(rootWord)
                                ?: LinkedList()
                        rootWordLinks.add(word)

                        loadword(word, rootWordLinks.size)


                        VariablesCi.ciContext?.currentWordRootLinks?.put(rootWord, rootWordLinks)
                    }
                }

                return true
            }
        }


        gotoWordFromDict(0)


        action_speak.setOnClickListener {
            VariablesCi.ciContext!!.currentword?.let {
                speech!!.speak(it, TextToSpeech.QUEUE_FLUSH, null, "oops")
            }
        }

        action_next.setOnClickListener {
            gotoWordFromDict(1)
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

        }


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
        } else {
            val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
            dialog.setTitle("还未学完单词,仍然退出?")
            dialog.setPositiveButton("退出") { dialog, which ->
                super.onBackPressed()
                speech?.stop()
                speech?.shutdown()
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
//                speech!!.speak(it, TextToSpeech.QUEUE_FLUSH, null, "oops")
//            }
        } else {
            Toast.makeText(this, "手机不支持合成语音", Toast.LENGTH_SHORT).show()
        }
    }

}