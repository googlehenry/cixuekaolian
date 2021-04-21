package com.viastub.kao100.module.my

import android.content.Intent
import android.view.View
import android.widget.RadioGroup
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.SearchedWordHistoryAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.beans.SearchMode
import com.viastub.kao100.beans.SearchedWord
import com.viastub.kao100.db.MyWordHistory
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.module.ci.CiPage0Activity
import com.viastub.kao100.utils.VariablesCi
import com.viastub.kao100.utils.VariablesKao
import kotlinx.android.synthetic.main.my_ci.*
import java.util.*


class MyCiPage : BaseActivity(), View.OnClickListener {
    override fun id(): Int {
        return R.layout.my_ci
    }

    var wordHistory: List<SearchedWord> = listOf()

    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        //https://www.jianshu.com/p/e68a0b5fd383
        recycler_view_high.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        radiogroup_start.isChecked = true
        radiogroup_all.isChecked = true

        radiogroup_group.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.radiogroup_start -> VariablesCi.searchMode = SearchMode.START
                    R.id.radiogroup_contains -> VariablesCi.searchMode = SearchMode.CONTAINS
                }

                searchView.query.toString().trim()?.let {
                    if (it.isNotEmpty()) {
                        searchItem(it)?.let { updateLayout(it) }
                    }
                }

            }
        })
        radiogroup_group2.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.radiogroup_all -> VariablesCi.myWordShowFavoriteOnly = false
                    R.id.radiogroup_favorite -> VariablesCi.myWordShowFavoriteOnly = true
                }

                searchView.query.toString().trim()?.let {
                    searchItem(it)?.let { updateLayout(it) }
                }

            }
        })

    }

    override fun onResume() {
        super.onResume()
        loadMyHistory()
    }

    private fun loadMyHistory() {
        awaitAsync({
            var roomDb = RoomDB.get(this)
            roomDb.myWordHistory().getByUserId(VariablesKao.currentUserId)
        }, {
            it?.let {
                updateUI(it)
            }
        })
    }

    fun updateUI(wordHistory: List<MyWordHistory>) {
        this.wordHistory =
            wordHistory.map { SearchedWord(it.word!!, "", it.visitCount, it.isFavorite == true) }
                .sortedByDescending { it.count }
                ?.toMutableList()

        recycler_view_high.adapter = SearchedWordHistoryAdapter(this)


        searchView.isSubmitButtonEnabled = false
        searchView.onActionViewExpanded()
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchItem(newText)?.let { updateLayout(it) }
                return true
            }

        })

        searchItem(searchView.query.toString())?.let { updateLayout(it) }

    }

    fun searchItem(name: String?): List<SearchedWord> {
        val filteredList = ArrayList<SearchedWord>()
        //SearchedWord("unless", "柯林斯双解")

        var fullset = this.wordHistory


        if (VariablesCi.myWordShowFavoriteOnly) {
            fullset = fullset.filter { it.favorite }
        }

        name?.trim()?.let { enteredKey ->

            if (enteredKey.isNotBlank()) {

                fullset?.filter {
                    when (VariablesCi.searchMode) {
                        SearchMode.START -> it.word.startsWith(enteredKey, ignoreCase = true)
                        SearchMode.MIDDLE -> {
                            var idx = it.word.indexOf(enteredKey, 0, ignoreCase = true)
                            var endix = idx + enteredKey.length
                            (idx > 0 && endix < it.word.length - 1)
                        }
                        SearchMode.END -> it.word.endsWith(enteredKey, ignoreCase = true)
                        SearchMode.CONTAINS -> it.word.contains(enteredKey, ignoreCase = true)
                    }
                }?.let {
                    filteredList.addAll(it)
                }

            }
        }
        if (filteredList.isEmpty()) {
            fullset?.let {
                filteredList.addAll(it)
            }
        }

        return filteredList
    }

    // 更新数据
    fun updateLayout(obj: List<SearchedWord>) {
        (recycler_view_high.adapter as SearchedWordHistoryAdapter).data = obj.toMutableList()
        recycler_view_high.adapter?.notifyDataSetChanged()
    }


    override fun onClick(v: View?) {
        var word = v?.getTag(R.id.searched_word_item_holder_root)?.let { it as SearchedWord }

        word?.let {
            var intent = Intent(this, CiPage0Activity::class.java)
            val wordList =
                (recycler_view_high.adapter as SearchedWordHistoryAdapter).data.map { it.word }

            goToDictionary(wordList, it.word)

        }
    }

}