package com.qianli.cixuekaolian.module.ci

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.adapter.SearchedWordAdapter
import com.qianli.cixuekaolian.base.BaseFragment
import com.qianli.cixuekaolian.beans.SearchedWord
import kotlinx.android.synthetic.main.fragment_ci.*
import java.util.*


class CiFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mSearchedWordAdapter: SearchedWordAdapter
    private val data = ArrayList<SearchedWord>()
    override fun id(): Int {
        return R.layout.fragment_ci
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {

        //https://www.jianshu.com/p/e68a0b5fd383
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                mContext,
                DividerItemDecoration.VERTICAL
            )
        )
        mSearchedWordAdapter = SearchedWordAdapter(this)
        mSearchedWordAdapter.data = data
        recycler_view.adapter = mSearchedWordAdapter

        loadData()

        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchItem(newText)?.let { updateLayout(it) }
                return false
            }

        })


    }

    fun searchItem(name: String?): List<SearchedWord> {
        val mSearchList = ArrayList<SearchedWord>()
        return if (name == null) data else data.filter {
            it.word
                .contains(name, ignoreCase = true)
        }
    }

    // 更新数据
    fun updateLayout(obj: List<SearchedWord>) {
        mSearchedWordAdapter.data = obj.toMutableList()
        recycler_view.adapter = mSearchedWordAdapter
    }

    // 测试数据
    fun loadData() {
        data.add(SearchedWord("aacwd", "testing"))
        data.add(SearchedWord("adw", "testing"))
        data.add(SearchedWord("aabc", "testing"))
        data.add(SearchedWord("aecsw", "testing"))
        data.add(SearchedWord("bdws", "testing"))
        data.add(SearchedWord("dcsew", "testing"))
        data.add(SearchedWord("xcsd", "testing"))
        data.add(SearchedWord("sdcwe", "testing"))
        data.add(SearchedWord("zxcsds", "testing"))
        data.add(SearchedWord("wesd", "testing"))
        data.add(SearchedWord("adtd", "testing"))
    }


    override fun onClick(v: View?) {
        startActivity(
            Intent(mContext, CiPage0Activity::class.java)
        )
    }

}
