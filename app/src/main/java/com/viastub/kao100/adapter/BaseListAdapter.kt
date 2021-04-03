package com.viastub.kao100.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.IdRes

abstract class BaseListAdapter<T>(
    var context: Context,
    private var layoutId: Int,
    var data: MutableList<T>? = null
) : BaseAdapter() {
    constructor(context: Context, layoutId: Int) : this(context, layoutId, null)

    abstract fun convert(viewHolder: ViewHolder, item: T)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: ViewHolder
        val view: View
        if (convertView == null) {
            view = LayoutInflater.from(context)
                .inflate(layoutId, parent, false);
            vh = ViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as BaseListAdapter<T>.ViewHolder
        }

        convert(vh, data!![position])

        return view
    }


    inner class ViewHolder(private val view: View) {
        fun <T> findViewById(@IdRes viewId: Int): T {
            return view.findViewById(viewId) as T
        }
    }

    override fun getItem(position: Int): T = data!![position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getCount(): Int = data!!.size
}
