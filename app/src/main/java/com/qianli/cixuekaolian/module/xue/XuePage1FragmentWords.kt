package com.qianli.cixuekaolian.module.xue

import android.os.Bundle
import android.view.View
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.base.BaseFragment
import kotlinx.android.synthetic.main.activity_xue_detail_page_frag_words.*

class XuePage1FragmentWords : BaseFragment() {

    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_words
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
//        wordlist.typeface= Typeface.defaultFromStyle(Typeface.BOLD)
        wordlist.text = """
            Unit 1
            pen [pen]钢笔
            pencil ['pensəl]铅笔
            pencil-case ['pensəlkeis]铅笔盒
            ruler ['ru:lə]尺子
            eraser [i'reizə]橡皮
            crayon ['kreiən]蜡笔
            book [buk]书
            bag [bæɡ]书包
            sharpener['ʃɑ:pənə]卷笔刀
            school [sku:l]学校
        """.trimIndent()
    }

}