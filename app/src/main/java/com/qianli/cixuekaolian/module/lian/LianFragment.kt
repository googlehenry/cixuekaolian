package com.qianli.cixuekaolian.module.lian

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.adapter.ExcerciseBookAdapter
import com.qianli.cixuekaolian.base.BaseFragment
import com.qianli.cixuekaolian.beans.ExcerciseBook
import kotlinx.android.synthetic.main.fragment_lian.*

class LianFragment : BaseFragment(), View.OnClickListener {


    override fun id(): Int {
        return R.layout.fragment_lian
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        var adapter = ExcerciseBookAdapter(this)
        prepareData(adapter)
        recycler_view_excercise_navs.adapter = adapter


//        //https://www.jianshu.com/p/e68a0b5fd383
//        recycler_view_excercise_navs.addItemDecoration(
//            DividerItemDecoration(
//                mContext,
//                DividerItemDecoration.VERTICAL
//            )
//        )

//        var navs = mutableListOf<ExcerciseBook>(
//            ExcerciseBook(1,"高\n考"),
//            ExcerciseBook(2,"中\n考"),
//            ExcerciseBook(3,"小\n升\n初"),
//            ExcerciseBook(4,"教\n材\n配\n套")
//        )
//        vertical_tab_layout.setTabAdapter(object : TabAdapter {
//            override fun getCount(): Int {
//                return navs.size
//            }
//
//            override fun getBadge(position: Int): ITabView.TabBadge? {
//                return null
//            }
//
//            override fun getIcon(position: Int): ITabView.TabIcon? {
//                return null
//            }
//
//            override fun getTitle(position: Int): ITabView.TabTitle {
//                return ITabView.TabTitle.Builder()
//                    .setContent(navs[position].shortName)
//                    .setTextColor(-0xde690d, -0x8a8a8b)
//                    .setTextSize(16)
//
//                    .build()
//            }
//
//            override fun getBackground(position: Int): Int {
//                return 0
//            }
//        })
//
//        for (idx in 0 until vertical_tab_layout.tabCount){
//            val qtab = (vertical_tab_layout.getTabAt(idx) as QTabView)
//        }

    }

    private fun prepareData(adapter: ExcerciseBookAdapter) {
        adapter.addData(ExcerciseBook(1, "高中"))
        adapter.addData(ExcerciseBook(2, "初中"))
        adapter.addData(ExcerciseBook(3, "小升初"))
        adapter.addData(ExcerciseBook(4, "教材配套"))
        adapter.addData(ExcerciseBook(1, "高中"))
        adapter.addData(ExcerciseBook(2, "初中"))
        adapter.addData(ExcerciseBook(3, "小升初"))
        adapter.addData(ExcerciseBook(4, "教材配套"))
        adapter.addData(ExcerciseBook(1, "高中"))
        adapter.addData(ExcerciseBook(2, "初中"))
        adapter.addData(ExcerciseBook(3, "小升初"))
        adapter.addData(ExcerciseBook(4, "教材配套"))
    }

    var lastSelectedItem: ConstraintLayout? = null
    override fun onClick(v: View?) {

        lastSelectedItem?.let {
            it.setBackgroundResource(R.drawable.shape_button_half_rounded)
            it.findViewById<TextView>(R.id.nav_book_title)
                ?.setTextColor(Color.parseColor("#FFFFFF"))
        }
        v?.setBackgroundResource(R.drawable.shape_button_all_rounded_white)
//        v?.setBackgroundColor(Color.parseColor("#FFFFFF"))
        v?.findViewById<TextView>(R.id.nav_book_title)?.setTextColor(Color.parseColor("#7C4DFF"))
        lastSelectedItem = v as ConstraintLayout

        val excerciseBook: ExcerciseBook? =
            v?.getTag(R.id.excercise_nav_item_holder) as ExcerciseBook
        toast(excerciseBook?.shortName ?: "no books selected")
    }


}