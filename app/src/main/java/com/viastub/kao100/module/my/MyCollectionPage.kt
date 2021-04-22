package com.viastub.kao100.module.my

import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.MyCollectItemAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.MyCollectedNote
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.VariablesKao
import kotlinx.android.synthetic.main.my_ci.*
import java.util.*


class MyCollectionPage : BaseActivity() {
    override fun id(): Int {
        return R.layout.my_collection
    }

    var myCollectedNotesDB: List<MyCollectedNote> = listOf()

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

        loadMyNotes()
    }


    private fun loadMyNotes() {
        awaitAsync({
            var roomDb = RoomDB.get(this)
            roomDb.myCollectedNote().getByUserId(VariablesKao.currentUserId)
        }, {
            it?.let {
                updateUI(it)
            }
        })
    }

    fun updateUI(wordHistory: List<MyCollectedNote>) {
        //TODO not working
        var adapter = MyCollectItemAdapter()

        this.myCollectedNotesDB = wordHistory.sortedByDescending { it.id }
        adapter.data = this.myCollectedNotesDB.toMutableList()

        recycler_view_high.adapter = adapter
        title_high.text = "我的收集(共${myCollectedNotesDB.size}条)"
        searchView.isSubmitButtonEnabled = false
        searchView.onActionViewExpanded()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchItem(newText).let { updateLayout(it) }
                return true
            }

        })

    }

    fun searchItem(name: String?): List<MyCollectedNote> {
        val filteredList = ArrayList<MyCollectedNote>()
        //SearchedWord("unless", "柯林斯双解")

        var fullset = this.myCollectedNotesDB


        name?.trim()?.let { enteredKey ->

            if (enteredKey.isNotBlank()) {

                fullset?.filter {
                    it.collectedText?.let { it.contains(enteredKey, true) } ?: false
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
    fun updateLayout(obj: List<MyCollectedNote>) {
        (recycler_view_high.adapter as MyCollectItemAdapter).data = obj.toMutableList()
        recycler_view_high.adapter?.notifyDataSetChanged()
    }


}