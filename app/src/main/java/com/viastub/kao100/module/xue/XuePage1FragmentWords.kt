package com.viastub.kao100.module.xue

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.WordLineAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.db.TeachingBookWord
import kotlinx.android.synthetic.main.activity_xue_detail_page_frag_words.*

class XuePage1FragmentWords(
    var bookWordItemsDb: MutableList<TeachingBookWord>?
) : BaseFragment(), View.OnClickListener {

    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_words
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        bookWordItemsDb?.let {
            var adapter = WordLineAdapter(this)
            adapter.data = it
            recycler_view_wordlist.adapter = adapter

        }

        recycler_view_wordlist.addItemDecoration(
            DividerItemDecoration(
                mContext,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onClick(v: View?) {
        var wordItem = v?.getTag(R.id.word_line_holder)?.let { it as TeachingBookWord }
        var wordlist = bookWordItemsDb?.map { it.name }
        wordItem?.let {
            goToDictionary(wordlist!!, it.name)
        }
    }

    override fun onResume() {
        super.onResume()
        toast("长按单词进入词典")
    }

}