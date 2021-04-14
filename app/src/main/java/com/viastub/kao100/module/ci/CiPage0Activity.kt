package com.viastub.kao100.module.ci

import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.utils.VariablesCi
import kotlinx.android.synthetic.main.activity_ci_word_detail_page.*

class CiPage0Activity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_ci_word_detail_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()
        var word = intent?.extras?.get("word")?.let { it as String }

        title_high.text = "查询结果:$word"
        word?.let { wd ->
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
        header_action.setOnClickListener {
            VariablesCi.ciContext?.dictConfig?.mdict?.let {
                ci_word_detail.loadDataWithBaseURL(
                    "file:///android_asset/www/",
                    it.getRecordAt(it.lookUp(word)),
                    "text/html",
                    "UTF-16LE",
                    null
                )
            }
        }


        header_back.setOnClickListener { onBackPressed() }
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
        return "<html>$head<body>$data</body></html>"
    }

}