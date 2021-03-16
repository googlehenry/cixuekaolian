package com.qianli.cixuekaolian.module.lian

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.postDelayed
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.adapter.ExcerciseBookAdapter
import com.qianli.cixuekaolian.adapter.ExcerciseByTypeAdapter
import com.qianli.cixuekaolian.adapter.ExcerciseByUnitAdapter
import com.qianli.cixuekaolian.base.BaseFragment
import com.qianli.cixuekaolian.beans.ExcerciseBook
import com.qianli.cixuekaolian.beans.ExcerciseByChapter
import com.qianli.cixuekaolian.beans.ExcerciseByType
import com.qianli.cixuekaolian.beans.ExcerciseByUnit
import kotlinx.android.synthetic.main.fragment_lian.*

class LianFragment : BaseFragment(), View.OnClickListener {


    override fun id(): Int {
        return R.layout.fragment_lian
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        var adapter = ExcerciseBookAdapter(this)
        adapter.data = prepareData()
        recycler_view_excercise_navs.adapter = adapter

        //https://www.jianshu.com/p/e68a0b5fd383
//        recycler_view_excercise_nav_groups.addItemDecoration(
//            DividerItemDecoration(
//                mContext,
//                DividerItemDecoration.VERTICAL
//            )
//        )

        recycler_view_excercise_navs.postDelayed(100) {
            recycler_view_excercise_navs.getChildAt(0).performClick()
        }

    }

    var lastSelectedItem: ConstraintLayout? = null
    override fun onClick(v: View?) {
        val excerciseBook: ExcerciseBook? =
            v?.getTag(R.id.excercise_nav_item_holder) as ExcerciseBook

        if (excerciseBook?.id!! >= 0) {

            excerciseBook?.let { loadBook(it) }

            lastSelectedItem?.let {
                it.setBackgroundResource(R.drawable.shape_button_half_rounded)
                it.findViewById<TextView>(R.id.nav_book_title)
                    ?.setTextColor(Color.parseColor("#FFFFFF"))
            }
            v?.setBackgroundResource(R.drawable.shape_button_all_rounded_white)
            v?.findViewById<TextView>(R.id.nav_book_title)
                ?.setTextColor(Color.parseColor("#7C4DFF"))
            lastSelectedItem = v as ConstraintLayout
        } else {
            toast("没有更多内容了")
        }
    }

    private fun loadBook(book: ExcerciseBook) {
        book.types?.let {
            var adapterTypes = ExcerciseByTypeAdapter()
            adapterTypes.data = it
            recycler_view_excercise_nav_groups.adapter = adapterTypes
        }

        book.chapters?.let {
            var adapterChapters = ExcerciseByUnitAdapter()
            var units = mutableListOf<ExcerciseByUnit>()

            it.forEach { chap ->
                chap.units?.forEach { unit ->
                    units.add(
                        ExcerciseByUnit(
                            0,
                            "${chap.shortName} _ ${unit.shortName}",
                            total = unit.total
                        )
                    )
                }!!
            }


            adapterChapters.data = units.mapIndexed { index, excerciseByUnit ->
                excerciseByUnit.id = index + 1
                excerciseByUnit
            }.toMutableList()
            recycler_view_excercise_nav_groups.adapter = adapterChapters
        }
    }

    private fun prepareData(): MutableList<ExcerciseBook> {
        var list = mutableListOf<ExcerciseBook>()
        var textBooks = ExcerciseBook(0, "教材配套")
        textBooks.chapters = mutableListOf(
            ExcerciseByChapter(
                1, "人教版3上", units = mutableListOf(
                    ExcerciseByUnit(0, "第1单元", total = 122),
                    ExcerciseByUnit(0, "第2单元", total = 122),
                    ExcerciseByUnit(0, "第3单元", total = 122),
                    ExcerciseByUnit(0, "第4单元", total = 122),
                    ExcerciseByUnit(0, "第5单元", total = 122),
                    ExcerciseByUnit(0, "第6单元", total = 122),
                    ExcerciseByUnit(0, "期末考试", total = 324),
                )
            ),
            ExcerciseByChapter(
                2, "人教版3下", units = mutableListOf(
                    ExcerciseByUnit(0, "第1单元", total = 122),
                    ExcerciseByUnit(0, "第2单元", total = 122),
                    ExcerciseByUnit(0, "第3单元", total = 122),
                    ExcerciseByUnit(0, "第4单元", total = 122),
                    ExcerciseByUnit(0, "第5单元", total = 122),
                    ExcerciseByUnit(0, "第6单元", total = 122),
                    ExcerciseByUnit(0, "期末考试", total = 122),
                )
            ),
            ExcerciseByChapter(
                3, "人教版4上", units = mutableListOf(
                    ExcerciseByUnit(0, "第1单元", total = 122),
                    ExcerciseByUnit(0, "第2单元", total = 122),
                    ExcerciseByUnit(0, "第3单元", total = 122),
                    ExcerciseByUnit(0, "第4单元", total = 122),
                    ExcerciseByUnit(0, "第5单元", total = 122),
                    ExcerciseByUnit(0, "第6单元", total = 122),
                    ExcerciseByUnit(0, "期末考试", total = 122),
                )
            ),
            ExcerciseByChapter(
                4, "人教版4下", units = mutableListOf(
                    ExcerciseByUnit(0, "第1单元", total = 122),
                    ExcerciseByUnit(0, "第2单元", total = 122),
                    ExcerciseByUnit(0, "第3单元", total = 122),
                    ExcerciseByUnit(0, "第4单元", total = 122),
                    ExcerciseByUnit(0, "第5单元", total = 122),
                    ExcerciseByUnit(0, "第6单元", total = 122),
                    ExcerciseByUnit(0, "期末考试", total = 122),
                )
            ),
            ExcerciseByChapter(
                5, "人教版5上", units = mutableListOf(
                    ExcerciseByUnit(0, "第1单元", total = 122),
                    ExcerciseByUnit(0, "第2单元", total = 122),
                    ExcerciseByUnit(0, "第3单元", total = 122),
                    ExcerciseByUnit(0, "第4单元", total = 122),
                    ExcerciseByUnit(0, "第5单元", total = 122),
                    ExcerciseByUnit(0, "第6单元", total = 122),
                    ExcerciseByUnit(0, "期末考试", total = 122),
                )
            ),
            ExcerciseByChapter(
                6, "人教版5下", units = mutableListOf(
                    ExcerciseByUnit(0, "第1单元", total = 122),
                    ExcerciseByUnit(0, "第2单元", total = 122),
                    ExcerciseByUnit(0, "第3单元", total = 122),
                    ExcerciseByUnit(0, "第4单元", total = 122),
                    ExcerciseByUnit(0, "第5单元", total = 122),
                    ExcerciseByUnit(0, "第6单元", total = 122),
                    ExcerciseByUnit(0, "期末考试", total = 122),
                )
            ),
            ExcerciseByChapter(
                7, "人教版6上", units = mutableListOf(
                    ExcerciseByUnit(0, "第1单元", total = 122),
                    ExcerciseByUnit(0, "第2单元", total = 122),
                    ExcerciseByUnit(0, "第3单元", total = 122),
                    ExcerciseByUnit(0, "第4单元", total = 122),
                    ExcerciseByUnit(0, "第5单元", total = 122),
                    ExcerciseByUnit(0, "第6单元", total = 122),
                    ExcerciseByUnit(0, "期末考试", total = 122),
                )
            ),
            ExcerciseByChapter(
                8, "人教版7下", units = mutableListOf(
                    ExcerciseByUnit(0, "第1单元", total = 122),
                    ExcerciseByUnit(0, "第2单元", total = 122),
                    ExcerciseByUnit(0, "第3单元", total = 122),
                    ExcerciseByUnit(0, "第4单元", total = 122),
                    ExcerciseByUnit(0, "第5单元", total = 122),
                    ExcerciseByUnit(0, "第6单元", total = 122),
                    ExcerciseByUnit(0, "期末考试", total = 122),
                )
            ),
        )
        list.add(textBooks)

        var highSchool = ExcerciseBook(1, "高考︹全国︺")
        highSchool.types = mutableListOf(
            ExcerciseByType(1, "单句改错", total = 64),
            ExcerciseByType(2, "语法填空", total = 147),
            ExcerciseByType(3, "短文改错", total = 231),
            ExcerciseByType(4, "阅读理解", total = 638),
            ExcerciseByType(5, "完形填空", total = 854),
            ExcerciseByType(6, "书面表达", total = 45)
        )
        list.add(highSchool)//︹︺︵︶

        var middleSchool = ExcerciseBook(2, "中考︹全国︺")
        middleSchool.types = mutableListOf(
            ExcerciseByType(1, "单句改错", total = 432),
            ExcerciseByType(2, "短文改错", total = 134),
            ExcerciseByType(3, "阅读理解", total = 636),
            ExcerciseByType(4, "书面表达", total = 89)
        )
        list.add(middleSchool)//︹︺︵︶

        var primarySchool = ExcerciseBook(3, "小升初︹全国︺")
        primarySchool.types = mutableListOf(
            ExcerciseByType(1, "单句填空", total = 576),
            ExcerciseByType(2, "单句改错", total = 873),
        )
        list.add(primarySchool)//︹︺︵︶

//        var fakeForUI = ExcerciseBook(-1, "")
//        list.add(fakeForUI)

        return list
    }


}