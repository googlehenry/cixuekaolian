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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viastub.kao100.R
import com.viastub.kao100.adapter.ExcerciseByBookAdapter
import com.viastub.kao100.adapter.ExcerciseTargetsAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.db.PracticeBook
import com.viastub.kao100.db.PracticeSection
import com.viastub.kao100.db.PracticeTarget
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.VariablesKao
import kotlinx.android.synthetic.main.fragment_lian.*

class LianFragment : BaseFragment(), View.OnClickListener, OnExcercistStartListener {


    override fun id(): Int {
        return R.layout.fragment_lian
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        doAsync(0, {
            val roomDB: RoomDB = RoomDB.get(applicationContext = mContext)
            roomDB.practiceTarget().getAll()?.toMutableList()
        },
            {
                updateUI(it)
            })

    }

    private fun loadBooksFromDB(
        target: PracticeTarget,
        roomDB: RoomDB
    ) {
        var books = target.bookIds()?.map { bookId ->
            var bookDb = roomDB.practiceBook().getById(bookId)

            bookDb?.bindUnitSectionsDBToThis(bookDb?.unitSectionIds()?.let { sectionIds ->
                var sections = roomDB.practiceSection().getByIds(sectionIds)
                sections?.forEach { sec ->
                    sec.mySectionPracticeHistory = roomDB.mySectionPracticeHistory()
                        .getByUserIdAndSectionId(VariablesKao.currentUserId, sec?.id!!)
                }
                sections
            }?.toMutableList())

            bookDb
        }?.filterNotNull()?.toMutableList()

        target.bindBooksDBToThis(books = books)
    }

    private fun updateUI(targets: MutableList<PracticeTarget>?) {
        targets?.let {
            var adapter = ExcerciseTargetsAdapter(this)
            adapter.data = it
            recycler_view_lian_targets.adapter = adapter
            recycler_view_lian_targets.postDelayed(100) {
                recycler_view_lian_targets.getChildAt(0).performClick()
            }
        }

    }

    var lastSelectedItem: CardView? = null
    override fun onClick(v: View?) {
        val excerciseTarget: PracticeTarget? =
            v?.getTag(R.id.excercise_nav_item_holder) as PracticeTarget

        if (excerciseTarget?.id!! >= 0) {
            excerciseTarget?.let { loadBooksOfTarget(it) }
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

    private fun loadBooksOfTarget(target: PracticeTarget) {
        doAsync(0, {
            loadBooksFromDB(target, RoomDB.get(applicationContext = mContext))
        }, {
            target.booksDb?.let {
                var adapterBooks = ExcerciseByBookAdapter(object : View.OnClickListener {
                    override fun onClick(v: View?) {//onclick books
                        val unitHolder = v?.findViewById<RecyclerView>(R.id.recycler_units)
                        val folderImageView =
                            v?.findViewById<ImageView>(R.id.lian_book_item_show_more)
                        unitHolder?.visibility =
                            if (unitHolder?.isVisible == true) View.GONE else View.VISIBLE
                        folderImageView?.setImageResource(if (unitHolder?.isVisible == true) R.drawable.icon_button_minus else R.drawable.icon_button_plus)
                    }

                }, this, target)
                adapterBooks.data = it
                recycler_view_excercise_nav_groups.layoutManager = LinearLayoutManager(context)
                recycler_view_excercise_nav_groups.adapter = adapterBooks

            }
        })


    }

    override fun start(book: PracticeBook, unitSection: PracticeSection) {
        var intent = Intent(mContext, LianBookUnitSummaryActivity::class.java)
        intent.putExtra("section", unitSection)
        intent.putExtra("book", book)
        startActivity(intent)
    }


}

interface OnExcercistStartListener {
    fun start(book: PracticeBook, unitSection: PracticeSection)
}