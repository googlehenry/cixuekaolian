package com.viastub.kao100.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.viastub.kao100.R
import com.viastub.kao100.db.PracticeBook
import com.viastub.kao100.db.PracticeTarget
import com.viastub.kao100.module.lian.OnExcercistStartListener

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ExcerciseByBookAdapter(
    var itemClickListener: View.OnClickListener,
    val excerciseListener: OnExcercistStartListener, val excercistTarget: PracticeTarget
) :
    BaseQuickAdapter<PracticeBook, BaseViewHolder>(R.layout.fragment_lian_book_item),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: PracticeBook) {
        holder.setText(R.id.lian_book_item_seq, item.id.toString())
        holder.setText(R.id.lian_book_item_title, item.name)

        var groupIcon = holder.getView<ImageView>(R.id.lian_book_item_icon)
        groupIcon.visibility = View.VISIBLE
        item.coverImage()?.let {
            groupIcon.setImageURI(Uri.fromFile(it))
        }

        item.unitSectionsDb?.let {
            var recyclerViewUnits = holder.getView<RecyclerView>(R.id.recycler_units)
            val unitAdapter = ExcerciseByUnitAdapter(excerciseListener, item)
            unitAdapter.data = it
            recyclerViewUnits.adapter = unitAdapter
            recyclerViewUnits.layoutManager = GridLayoutManager(context, 2)
        }


        var bookItemHolder = holder.getView<CardView>(R.id.nav_group_holder)
        bookItemHolder.setTag(R.id.nav_group_holder, item)
        bookItemHolder.setOnClickListener { itemClickListener.onClick(it) }
    }

}