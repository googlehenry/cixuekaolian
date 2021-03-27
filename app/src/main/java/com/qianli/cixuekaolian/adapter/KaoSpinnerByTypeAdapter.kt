package com.qianli.cixuekaolian.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.beans.TestType

class KaoSpinnerByTypeAdapter(private val mContext: Context, var data: MutableList<TestType>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val _LayoutInflater = LayoutInflater.from(mContext)
        convertView = _LayoutInflater.inflate(R.layout.fragment_kao_spinner_type_item, null)
        val typeName = convertView.findViewById<TextView>(R.id.spinner_type_name) as TextView
        typeName.setText(data[position].shortName)
        return convertView
    }
}