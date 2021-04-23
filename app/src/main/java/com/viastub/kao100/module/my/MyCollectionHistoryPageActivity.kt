package com.viastub.kao100.module.my

import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.MyCollectItemAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.MyCollectedNote
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.VariablesKao
import com.viastub.kao100.wigets.CommonDialog
import kotlinx.android.synthetic.main.my_ci.*
import kotlinx.android.synthetic.main.my_ci.header_back
import kotlinx.android.synthetic.main.my_ci.recycler_view_high
import kotlinx.android.synthetic.main.my_ci.searchView
import kotlinx.android.synthetic.main.my_ci.title_high
import kotlinx.android.synthetic.main.my_collection.*
import java.util.*


class MyCollectionHistoryPageActivity : BaseActivity(), View.OnClickListener {
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

        header_action_refresh.setOnClickListener {
            loadMyNotes()
        }

    }

    override fun onResume() {
        super.onResume()
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
        var adapter = MyCollectItemAdapter(this)

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

    override fun onClick(v: View?) {
        var item = v?.getTag(R.id.note_collect_holder_root)?.let { it as MyCollectedNote }!!

        val dialog = CommonDialog(this)
        dialog.message = item.collectedText
        dialog.positive = "保存修改"
        dialog.negtive = "删除该项"
        dialog
            .setTitle("修改一个收集项")
            .setSingle(false)
            .setOnClickBottomListener(object : CommonDialog.OnClickBottomListener {
                override fun onPositiveClick() {
                    val inputName = dialog.internalEditText.text.toString()
                    doAsync {
                        RoomDB.get(applicationContext).myCollectedNote().insert(
                            MyCollectedNote(
                                id = item.id,
                                userId = VariablesKao.currentUserId,
                                collectedText = inputName,
                                tags = item.tags
                            )
                        )
                    }
                    dialog.message = ""
                    Toast.makeText(
                        this@MyCollectionHistoryPageActivity,
                        "收集项已保存",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    dialog.dismiss()
                }

                override fun onNegtiveClick() {
                    doAsync {
                        RoomDB.get(applicationContext).myCollectedNote().delete(item)
                    }
                    dialog.message = ""
                    Toast.makeText(
                        this@MyCollectionHistoryPageActivity,
                        "收集项已删除",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    dialog.dismiss()
                }
            })
        dialog.show()
        dialog.setCanceledOnTouchOutside(true)
    }


}