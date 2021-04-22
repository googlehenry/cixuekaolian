package com.viastub.kao100.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.viastub.kao100.R
import com.viastub.kao100.beans.TestPaperTag
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

class SimpleTagAdapter(var context: Context, data: List<TestPaperTag>) :
    TagAdapter<TestPaperTag>(data) {

    override fun getView(parent: FlowLayout?, position: Int, tag: TestPaperTag?): View {
        val tagName = LayoutInflater.from(context).inflate(
            R.layout.fragment_kao_test_paper_tag_item,
            parent, false
        ) as TextView

        if (tag != null) {
            tagName.text = tag.name
        }

        return tagName
    }

}