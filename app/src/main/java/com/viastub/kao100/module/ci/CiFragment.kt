package com.viastub.kao100.module.ci

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.SearchedWordAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.beans.SearchedWord
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
        recycler_view_high.addItemDecoration(
            DividerItemDecoration(
                mContext,
                DividerItemDecoration.VERTICAL
            )
        )
        mSearchedWordAdapter = SearchedWordAdapter(this)
        mSearchedWordAdapter.data = data
        recycler_view_high.adapter = mSearchedWordAdapter

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
        recycler_view_high.adapter = mSearchedWordAdapter
    }

    // 测试数据
    fun loadData() {
        data.add(SearchedWord("unless", "柯林斯双解"))
        data.add(SearchedWord("required", "柯林斯双解"))
        data.add(SearchedWord("by", "柯林斯双解"))
        data.add(SearchedWord("applicable", "柯林斯双解"))
        data.add(SearchedWord("law", "柯林斯双解"))
        data.add(SearchedWord("or", "柯林斯双解"))
        data.add(SearchedWord("agreed", "柯林斯双解"))
        data.add(SearchedWord("to", "柯林斯双解"))
        data.add(SearchedWord("in", "柯林斯双解"))
        data.add(SearchedWord("writing,", "柯林斯双解"))
        data.add(SearchedWord("software", "柯林斯双解"))
        data.add(SearchedWord("distributed", "柯林斯双解"))
        data.add(SearchedWord("under", "柯林斯双解"))
        data.add(SearchedWord("the", "柯林斯双解"))
        data.add(SearchedWord("license", "柯林斯双解"))
        data.add(SearchedWord("is", "柯林斯双解"))
        data.add(SearchedWord("on", "柯林斯双解"))
        data.add(SearchedWord("an", "柯林斯双解"))
        data.add(SearchedWord("as", "柯林斯双解"))
        data.add(SearchedWord("basis", "柯林斯双解"))
        data.add(SearchedWord("without", "柯林斯双解"))
        data.add(SearchedWord("warranties", "柯林斯双解"))
        data.add(SearchedWord("conditions", "柯林斯双解"))
        data.add(SearchedWord("of", "柯林斯双解"))
        data.add(SearchedWord("any", "柯林斯双解"))
        data.add(SearchedWord("kind", "柯林斯双解"))
        data.add(SearchedWord("either", "柯林斯双解"))
        data.add(SearchedWord("express", "柯林斯双解"))
        data.add(SearchedWord("implied", "柯林斯双解"))
        data.add(SearchedWord("see", "柯林斯双解"))
        data.add(SearchedWord("for", "柯林斯双解"))
        data.add(SearchedWord("specific", "柯林斯双解"))
        data.add(SearchedWord("language", "柯林斯双解"))
        data.add(SearchedWord("governing", "柯林斯双解"))
        data.add(SearchedWord("permissions", "柯林斯双解"))
        data.add(SearchedWord("and", "柯林斯双解"))
        data.add(SearchedWord("limitations", "柯林斯双解"))
        data.add(SearchedWord("license", "柯林斯双解"))
    }


    override fun onClick(v: View?) {
        startActivity(
            Intent(mContext, CiPage0Activity::class.java)
        )
    }

}
