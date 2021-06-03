package com.viastub.kao100.module.ci

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.knziha.plod.dictionary.mdict
import com.viastub.kao100.R
import com.viastub.kao100.adapter.SearchedWordAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.beans.CiContext
import com.viastub.kao100.beans.SearchMode
import com.viastub.kao100.beans.SearchedWord
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.VariablesCi
import kotlinx.android.synthetic.main.fragment_ci.*
import java.io.File
import java.util.*


class CiFragment : BaseFragment(), View.OnClickListener {

    override fun id(): Int {
        return R.layout.fragment_ci
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {

        VariablesCi.ciContext = CiContext()
        //https://www.jianshu.com/p/e68a0b5fd383
        recycler_view_high.addItemDecoration(
            DividerItemDecoration(
                mContext,
                DividerItemDecoration.VERTICAL
            )
        )

        loadDictWithId(1)


        radiogroup_start.isChecked = true
        radiogroup_group.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.radiogroup_start -> VariablesCi.searchMode = SearchMode.START
                    R.id.radiogroup_middle -> VariablesCi.searchMode = SearchMode.MIDDLE
                    R.id.radiogroup_end -> VariablesCi.searchMode = SearchMode.END
                    R.id.radiogroup_contains -> VariablesCi.searchMode = SearchMode.CONTAINS
                }

                searchView.query.toString().trim()?.let {
                    if (it.isNotEmpty()) {
                        searchItem(it)?.let { updateLayout(it) }
                    }
                }

            }
        })

        ci_search_zh_to_en.setOnClickListener {
            searchView.query.toString().trim()?.let {
                if (it.isNotEmpty()) {
                    goToFileDictionary(it)
                } else {
                    Toast.makeText(mContext, "请输入所查词语", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun loadDictWithId(dictId: Int) {
        doAsync(0, {
            var roomDb = RoomDB.get(mContext)
            var dictDb = roomDb.dictionaryConfig().getById(dictId)
            dictDb?.let {
                VariablesCi.ciContext?.dictConfig = it
            }
            val mdct = dictDb?.dictFilePath?.let {
                if (it.isNotBlank() && File(it).exists()) mdict(it) else null
            }
            dictDb?.mdict = mdct
            if (VariablesCi.ciContext?.wordKeys == null) {
                VariablesCi.ciContext?.wordKeys = mdct?.getKeys(null)?.toMutableList()
            }
            dictDb
        }, {
            it?.let {
                updateUI()
            }
        })
    }

    fun updateUI() {
        var mSearchedWordAdapter = SearchedWordAdapter(this)
        mSearchedWordAdapter.data =
            VariablesCi.ciContext?.wordKeys?.map { SearchedWord(it, "") }?.toMutableList()
                ?: mutableListOf()
        recycler_view_high.adapter = mSearchedWordAdapter

        searchView.setOnClickListener { searchView.onActionViewExpanded() }


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchItem(newText)?.let { updateLayout(it) }
                return true
            }

        })

        if (VariablesCi.ciContext?.wordKeys.isNullOrEmpty()) {
            what_if_no_content.visibility = View.VISIBLE
        } else {
            what_if_no_content.visibility = View.GONE
        }

    }

    fun searchItem(name: String?): List<SearchedWord> {
        val filteredList = ArrayList<String>()
        //SearchedWord("unless", "柯林斯双解")
        name?.trim()?.let { enteredKey ->

            if (enteredKey.isNotBlank()) {

                VariablesCi.ciContext?.wordKeys?.filter {
                    when (VariablesCi.searchMode) {
                        SearchMode.START -> it.startsWith(enteredKey, ignoreCase = true)
                        SearchMode.MIDDLE -> {
                            var idx = it.indexOf(enteredKey, 0, ignoreCase = true)
                            var endix = idx + enteredKey.length
                            (idx > 0 && endix < it.length - 1)
                        }
                        SearchMode.END -> it.endsWith(enteredKey, ignoreCase = true)
                        SearchMode.CONTAINS -> it.contains(enteredKey, ignoreCase = true)
                    }
                }?.let {
                    filteredList.addAll(it)
                }

            }
        }
        if (filteredList.isEmpty()) {
            VariablesCi.ciContext?.wordKeys?.let {
                filteredList.addAll(it)
            }
        }

        return filteredList.map { SearchedWord(it, "") }
    }

    // 更新数据
    fun updateLayout(obj: List<SearchedWord>) {
        (recycler_view_high.adapter as SearchedWordAdapter).data = obj.toMutableList()
        recycler_view_high.adapter?.notifyDataSetChanged()
    }


    override fun onClick(v: View?) {
        var word = v?.getTag(R.id.searched_word_item_holder_root)?.let { it as SearchedWord }

        word?.let {
            var intent = Intent(mContext, CiPage0Activity::class.java)
            val wordList = (recycler_view_high.adapter as SearchedWordAdapter).data.map { it.word }

            goToDictionary(wordList, it.word)

        }
    }

    override fun refresh() {
        loadDictWithId(1)
        Toast.makeText(mContext, "已刷新数据", Toast.LENGTH_SHORT).show()
    }

    fun goToFileDictionary(zhWord: String) {
        var intent = Intent(mContext, CiPage1Activity::class.java)
        intent.putExtra("zhWord", zhWord)
        startActivity(intent)
    }
}
