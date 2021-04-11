package com.viastub.kao100.module.xue

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.viastub.kao100.R
import com.viastub.kao100.adapter.BookItemAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.db.TeachingBook
import kotlinx.android.synthetic.main.fragment_xue.*

class XueFragment : BaseFragment(), View.OnClickListener {

    override fun id(): Int {
        return R.layout.fragment_xue
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {

        doAsync(0, {
            var roomDb = RoomDB.get(mContext)
            roomDb.teachingBook().getAll()?.toMutableList() ?: mutableListOf()
        }, {
            var adapter = BookItemAdapter(this)
            adapter.data = it
            recycler_view_textbooks.layoutManager = GridLayoutManager(mContext, 3)
            recycler_view_textbooks.adapter = adapter
        })


    }

    override fun onClick(v: View?) {
        var book = v?.getTag(R.id.book_item_holder)?.let { it as TeachingBook }
        book?.let {
            var intent = Intent(mContext, XuePage0Activity::class.java)
            intent.putExtra("teachingBook", it)
            startActivity(intent)
        }

    }

}