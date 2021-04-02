package com.viastub.kao100.module.lian

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viastub.kao100.R
import com.viastub.kao100.adapter.ExcerciseByBlockAdapter
import com.viastub.kao100.adapter.ExcerciseByBookAdapter
import com.viastub.kao100.adapter.ExcerciseByTypeAdapter
import com.viastub.kao100.adapter.ExcerciseTargetsAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.beans.*
import kotlinx.android.synthetic.main.fragment_lian.*

class LianFragment : BaseFragment(), View.OnClickListener, OnExcercistStartListener {


    override fun id(): Int {
        return R.layout.fragment_lian
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        var adapter = ExcerciseTargetsAdapter(this)
        adapter.data = prepareData()
        recycler_view_excercise_navs.adapter = adapter

        recycler_view_excercise_navs.postDelayed(100) {
            recycler_view_excercise_navs.getChildAt(0).performClick()
        }

    }

    var lastSelectedItem: CardView? = null
    override fun onClick(v: View?) {
        val excerciseTarget: ExcerciseTarget? =
            v?.getTag(R.id.excercise_nav_item_holder) as ExcerciseTarget

        if (excerciseTarget?.id!! >= 0) {

            excerciseTarget?.let { loadTarget(it) }

            lastSelectedItem?.let {
                it.setBackgroundResource(R.drawable.shape_button_half_rounded)
                it.findViewById<TextView>(R.id.nav_target_name)
                    ?.setTextColor(Color.parseColor("#FFFFFF"))
            }
            v?.setBackgroundResource(R.drawable.shape_button_all_rounded_white)
            v?.findViewById<TextView>(R.id.nav_target_name)
                ?.setTextColor(Color.parseColor("#000000"))
            lastSelectedItem = v as CardView
        } else {
            toast("没有更多内容了")
        }
    }

    private fun loadTarget(target: ExcerciseTarget) {

        target.blocks?.let {
            var adapterBlock = ExcerciseByBlockAdapter(this, target)
            adapterBlock.data = it
            recycler_view_excercise_nav_blocks.layoutManager =
                GridLayoutManager(context, 3)
            recycler_view_excercise_nav_blocks.adapter = adapterBlock
            recycler_view_excercise_nav_blocks.visibility = View.VISIBLE
        } ?: run {
            recycler_view_excercise_nav_blocks.visibility = View.GONE
        }

        target.types?.let {
            var adapterTypes = ExcerciseByTypeAdapter(this, target)
            adapterTypes.data = it
//            recycler_view_excercise_nav_groups.layoutManager =
//                GridLayoutManager(context, 2)
            recycler_view_excercise_nav_groups.adapter = adapterTypes
        }

        target.books?.let {
            var adapterBooks = ExcerciseByBookAdapter(object : View.OnClickListener {
                override fun onClick(v: View?) {//onclick books
                    val unitHolder = v?.findViewById<RecyclerView>(R.id.recycler_units)
                    val folderImageView = v?.findViewById<ImageView>(R.id.excercise_expand_collapse)
                    unitHolder?.visibility =
                        if (unitHolder?.isVisible == true) View.GONE else View.VISIBLE
                    folderImageView?.setImageResource(if (unitHolder?.isVisible == true) R.drawable.icon_button_minus else R.drawable.icon_button_plus)
                }

            }, this, target)
            adapterBooks.data = it
            recycler_view_excercise_nav_groups.layoutManager = LinearLayoutManager(context)
            recycler_view_excercise_nav_groups.adapter = adapterBooks

//            recycler_view_excercise_nav_groups.postDelayed(50) {
//                recycler_view_excercise_nav_groups.getChildAt(0).performClick()
//            }

            it.forEach { book ->
                book.error = 0
                book.done = 0
                book.total = 0

                book.units?.forEach {
                    book.error += it.error
                    book.done += it.done
                    book.total += it.total
                }
            }
        }
    }

    private fun prepareData(): MutableList<ExcerciseTarget> {
        var list = mutableListOf<ExcerciseTarget>()

        var textBooks = ExcerciseTarget(0, "教材配套")
        textBooks.books = mutableListOf(
            ExcerciseByBook(
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
            ExcerciseByBook(
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
            ExcerciseByBook(
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
            ExcerciseByBook(
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
            ExcerciseByBook(
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
            ExcerciseByBook(
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
            ExcerciseByBook(
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
            ExcerciseByBook(
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

        var highSchool = ExcerciseTarget(1, "高考︹全国︺", name = "全国高考")
        highSchool.types = mutableListOf(
            ExcerciseByType(1, "短文改错", total = 64),
            ExcerciseByType(2, "语法填空", total = 147),
            ExcerciseByType(3, "单项选择", total = 231),
            ExcerciseByType(4, "短文填空", total = 638),
            ExcerciseByType(5, "听力测试", total = 854),
            ExcerciseByType(6, "完形填空", total = 45),
            ExcerciseByType(7, "阅读理解", total = 345)
        )
//        highSchool.blocks = mutableListOf(
//            ExcerciseByBlock(1, "高考词汇", categories = mutableSetOf("短文改错")),
//            ExcerciseByBlock(1, "高考语法", categories = mutableSetOf("语法填空")),
//            ExcerciseByBlock(1, "句型搭配", categories = mutableSetOf("完形填空")),
//        )
        list.add(highSchool)//︹︺︵︶

        var middleSchool = ExcerciseTarget(2, "中考︹全国︺", name = "全国中考")
        middleSchool.types = mutableListOf(
            ExcerciseByType(1, "短文改错", total = 432),
            ExcerciseByType(2, "语法填空", total = 134),
            ExcerciseByType(3, "阅读理解", total = 636),
            ExcerciseByType(4, "听力测试", total = 89),
            ExcerciseByType(5, "汉语提示填写单词", total = 56),
            ExcerciseByType(6, "汉语提示完成句子", total = 92)
        )
//        middleSchool.blocks = mutableListOf(
//            ExcerciseByBlock(1, "中考词汇", categories = mutableSetOf("短文改错")),
//            ExcerciseByBlock(1, "中考语法", categories = mutableSetOf("语法填空")),
//            ExcerciseByBlock(1, "句型搭配", categories = mutableSetOf("完形填空")),
//        )
        list.add(middleSchool)//︹︺︵︶

//        var primarySchool = ExcerciseTarget(3, "小升初︹全国︺")
//        primarySchool.types = mutableListOf(
//            ExcerciseByType(1, "单项选择", total = 576),
//            ExcerciseByType(2, "听力测试", total = 873),
//        )
//
//        list.add(primarySchool)//︹︺︵︶

        return list
    }

    override fun start(book: ExcerciseByBook, excerciseByUnit: ExcerciseByUnit) {
        var intent = Intent(mContext, LianPage0Activity::class.java)
        intent.putExtra("category", "demo")
        intent.putExtra("target", book.shortName)

        startActivity(intent)
    }

    override fun start(target: ExcerciseTarget, excerciseByType: ExcerciseByType) {
        var intent = Intent(mContext, LianPage0Activity::class.java)
        intent.putExtra("category", excerciseByType.shortName)
        intent.putExtra("target", target.name)
        startActivity(intent)
    }

    override fun start(target: ExcerciseTarget, excerciseByBlock: ExcerciseByBlock) {
        var intent = Intent(mContext, LianPage0Activity::class.java)
        intent.putExtra("category", excerciseByBlock.categories?.first())
        intent.putExtra("target", target.name)
        startActivity(intent)
    }

}

interface OnExcercistStartListener {
    fun start(book: ExcerciseByBook, excerciseByUnit: ExcerciseByUnit)
    fun start(target: ExcerciseTarget, excerciseByType: ExcerciseByType)
    fun start(target: ExcerciseTarget, excerciseByBlock: ExcerciseByBlock)
}