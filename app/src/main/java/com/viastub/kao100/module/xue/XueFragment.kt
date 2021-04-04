package com.viastub.kao100.module.xue

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.viastub.kao100.R
import com.viastub.kao100.adapter.BookItemAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.beans.BookItem
import kotlinx.android.synthetic.main.fragment_xue.*

class XueFragment : BaseFragment(), View.OnClickListener {

    override fun id(): Int {
        return R.layout.fragment_xue
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view_high.layoutManager = GridLayoutManager(mContext, 3)
        recycler_view_middle.layoutManager = GridLayoutManager(mContext, 3)
        recycler_primary.layoutManager = GridLayoutManager(mContext, 3)
        var adapter = BookItemAdapter(this)
        var adapter2 = BookItemAdapter(this)
        var adapter3 = BookItemAdapter(this)
        adapter.data = mutableListOf(
            BookItem(1, "高一上", -1),
            BookItem(1, "高一下", -1),
            BookItem(1, "高二上", -1)
        )
        adapter2.data = mutableListOf(
            BookItem(1, "初一上", -1),
            BookItem(1, "初一下", -1),
            BookItem(1, "初二上", -1)
        )
        adapter3.data = mutableListOf(
            BookItem(1, "三年级上", -1),
            BookItem(1, "三年级下", -1),
            BookItem(1, "四年级上", -1)
        )
        recycler_view_high.adapter = adapter
        recycler_view_middle.adapter = adapter2
        recycler_primary.adapter = adapter3

    }

    override fun onClick(v: View?) {
        startActivity(
            Intent(mContext, XuePage0Activity::class.java)
        )
    }

}