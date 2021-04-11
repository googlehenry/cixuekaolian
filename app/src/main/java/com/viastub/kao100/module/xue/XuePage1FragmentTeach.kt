package com.viastub.kao100.module.xue

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.ExpandableTextAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.db.TeachingPoint
import kotlinx.android.synthetic.main.activity_xue_detail_page_frag_teach.*

class XuePage1FragmentTeach(var bookTeachingPointsDb: MutableList<TeachingPoint>?) :
    BaseFragment() {

    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_teach
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        bookTeachingPointsDb?.let {
            var teachItemAdapter = ExpandableTextAdapter()
            teachItemAdapter.data = it.mapIndexed { index, point ->
                point.sequence = index + 1
                point
            }.toMutableList()
            recycler_view_high.adapter = teachItemAdapter
        }


        recycler_view_high.addItemDecoration(
            DividerItemDecoration(
                mContext,
                DividerItemDecoration.VERTICAL
            )
        )


    }


}