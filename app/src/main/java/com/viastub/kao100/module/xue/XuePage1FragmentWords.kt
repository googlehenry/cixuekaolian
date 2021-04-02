package com.viastub.kao100.module.xue

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.WordLineAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.beans.WordLine
import kotlinx.android.synthetic.main.activity_xue_detail_page_frag_words.*

class XuePage1FragmentWords : BaseFragment() {

    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_words
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
//        wordlist.typeface= Typeface.defaultFromStyle(Typeface.BOLD)
        var adapter = WordLineAdapter()
        adapter.data = prepareDemoData()
        recycler_view_wordlist.adapter = adapter
        //https://www.jianshu.com/p/e68a0b5fd383
        recycler_view_wordlist.addItemDecoration(
            DividerItemDecoration(
                mContext,
                DividerItemDecoration.VERTICAL
            )
        )

    }

    private fun prepareDemoData(): MutableList<WordLine> {
        return wordList.lines().mapIndexed { index, s -> WordLine(index, s) }.toMutableList()
    }

    val wordList = """
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