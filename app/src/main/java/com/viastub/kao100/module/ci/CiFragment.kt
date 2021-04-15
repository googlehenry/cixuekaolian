package com.viastub.kao100.module.ci

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.SearchView
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

        doAsync(0, {
            var roomDb = RoomDB.get(mContext)
            var dictDb = roomDb.dictionaryConfig().getById(1)
            dictDb?.let {
                VariablesCi.ciContext?.dictConfig = it
            }
            val mdct = dictDb?.dictFilePath?.let { mdict(it) }
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

        radiogroup_start.isChecked = true
        radiogroup_group.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.radiogroup_start -> VariablesCi.searchMode = SearchMode.START
                    R.id.radiogroup_middle -> VariablesCi.searchMode = SearchMode.MIDDLE
                    R.id.radiogroup_end -> VariablesCi.searchMode = SearchMode.END
                }

                searchView.query.toString().trim()?.let {
                    searchItem(it)?.let { updateLayout(it) }
                }

            }
        })

    }

    fun updateUI() {
        var mSearchedWordAdapter = SearchedWordAdapter(this)
        mSearchedWordAdapter.data =
            VariablesCi.ciContext?.wordKeys?.map { SearchedWord(it, "") }?.toMutableList()
                ?: mutableListOf()
        recycler_view_high.adapter = mSearchedWordAdapter

        searchView.isSubmitButtonEnabled = false
        searchView.onActionViewExpanded()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchItem(newText)?.let { updateLayout(it) }
                return true
            }

        })
        searchView.clearFocus()
    }

    fun searchItem(name: String?): List<SearchedWord> {
        val filteredList = ArrayList<SearchedWord>()
        //SearchedWord("unless", "柯林斯双解")
        name?.trim()?.let { enteredKey ->

            if (enteredKey.isNotBlank()) {

                VariablesCi.ciContext?.wordKeys?.filter {
                    when (VariablesCi.searchMode) {
                        SearchMode.START -> it.startsWith(enteredKey, ignoreCase = true)
                        SearchMode.MIDDLE -> it.contains(enteredKey, ignoreCase = true)
                        SearchMode.END -> it.endsWith(enteredKey, ignoreCase = true)
                    }
                }?.map { SearchedWord(it, "") }?.let {
                    filteredList.addAll(it)
                }
            }
        }
        return filteredList
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
            val array = ArrayList<String>()
            val wordList = (recycler_view_high.adapter as SearchedWordAdapter).data.map { it.word }

            array.addAll(wordList)

            VariablesCi.ciContext!!.currentWordList = array.toMutableList()
            VariablesCi.ciContext!!.currentIndex = wordList.indexOf(word.word)

            startActivity(
                intent
            )
        }
    }

}
