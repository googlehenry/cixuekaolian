package com.viastub.kao100.module.ci

import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.knziha.plod.dictionary.mdict
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.RoomDB
import kotlinx.android.synthetic.main.activity_ci_word_detail_page.*


class CiPage1Activity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_ci_word_detail_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }
        var wordsx = intent?.extras?.get("zhWord") as String

        floating_button_add.setOnClickListener {
            val cm: ClipboardManager? =
                getSystemService(Context.CLIPBOARD_SERVICE)
                    ?.let { it as ClipboardManager }

            var txt = cm!!.primaryClip?.getItemAt(0)?.text.toString()
            addNewCollectDialog(txt, "中文词汇,手动添加")
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
                        gotoWordFromDict(word)
                    }
                }

                return true
            }
        }

        gotoWordFromDict(wordsx)
        bottom_function_bar.visibility = View.GONE
    }


    private fun gotoWordFromDict(wd: String) {
        loadword(wd)
    }

    var mdictZh: mdict? = null
    private fun loadword(wd: String, links: Int = 0) {

        awaitAsync({
            val roomDb = RoomDB.get(applicationContext)
            mdictZh = mdictZh ?: roomDb.dictionaryConfig().getById(2)?.dictFilePath?.let {
                mdict(it)
            }

            mdictZh?.let {
                var explanation = it.getRecordAt(it.lookUp(wd))
                explanation
            }
        }, {
            it?.let {
                ci_word_detail.loadDataWithBaseURL(
                    "file:///android_asset/www/",
                    getHtmlData(it),
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        })

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

}