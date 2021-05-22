package com.viastub.kao100.module.lian

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
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
import com.viastub.kao100.http.DownloadUtil
import com.viastub.kao100.http.RemoteAPIDataService
import com.viastub.kao100.utils.ActivityUtils
import com.viastub.kao100.utils.VariablesKao
import com.viastub.kao100.utils.VariablesMain
import com.yechaoa.yutilskt.LogUtilKt
import kotlinx.android.synthetic.main.fragment_lian.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LianFragment : BaseFragment(), View.OnClickListener, OnExcercistStartListener {


    override fun id(): Int {
        return R.layout.fragment_lian
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        loadTargets()
    }

    var currentTargetId: Int? = null
    private fun loadTargets(selectedTargetId: Int = 1) {
        doAsync(0, {
            val roomDB: RoomDB = RoomDB.get(applicationContext = mContext)
            roomDB.practiceTarget().getAll()?.toMutableList()
        },
            {
                updateUI(it, selectedTargetId)
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

    private fun updateUI(targets: MutableList<PracticeTarget>?, selectedTargetId: Int = 0) {
        targets?.let {
            var adapter = ExcerciseTargetsAdapter(this, selectedTargetId)
            adapter.data = it
            recycler_view_lian_targets.adapter = adapter

        }

        if (targets.isNullOrEmpty() && recycler_view_excercise_nav_groups.adapter != null) {
            var booksAdapter =
                (recycler_view_excercise_nav_groups.adapter as ExcerciseByBookAdapter)
            booksAdapter.data.clear()
            booksAdapter.notifyDataSetChanged()
        }
        if (targets.isNullOrEmpty()) {
            what_if_no_content.visibility = View.VISIBLE
        } else {
            what_if_no_content.visibility = View.GONE
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
        }
    }

    private fun loadBooksOfTarget(target: PracticeTarget) {
        currentTargetId = target.id

        doAsync(0, {
            loadBooksFromDB(target, RoomDB.get(applicationContext = mContext))
        }, {
            target.booksDb?.let { books ->
                var booksFiltered = books
                VariablesMain.selectedGrade?.let { gradeSelected ->
                    if (!gradeSelected.contains("所有") && !gradeSelected.contains("全部")) {
                        booksFiltered =
                            booksFiltered.filter { it.grade?.trim().equals(gradeSelected) }
                                .toMutableList()
                    }
                }

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
                booksFiltered.sortBy { it.displaySeq ?: 0 }
                booksFiltered.mapIndexed { index, practiceBook ->
                    practiceBook.displaySeq = index + 1
                }
                adapterBooks.data = booksFiltered
                recycler_view_excercise_nav_groups.layoutManager = LinearLayoutManager(context)
                recycler_view_excercise_nav_groups.adapter = adapterBooks

                if (booksFiltered.isNullOrEmpty()) {
                    what_if_no_content.visibility = View.VISIBLE
                } else {
                    what_if_no_content.visibility = View.GONE
                }
            }
        })


    }

    var downloadingId: Int? = null
    override fun start(book: PracticeBook, unitSection: PracticeSection) {
        if (downloadingId != null) {
            Toast.makeText(mContext, "正在下载...", Toast.LENGTH_SHORT).show()
            return
        }
        if (!unitSection.downloaded) {

            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(mContext)
            dialogBuilder.setTitle("正在下载")
            dialogBuilder.setCancelable(false)
            dialogBuilder.setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = dialogBuilder.show()

            doAsync(0, {
                var roomDb = RoomDB.get(mContext)

                RemoteAPIDataService.apis.getUnitById(unitSection.id!!)
                    .onErrorReturn {
                        unitSection
                    }.subscribe { section ->
                        var links = mutableListOf<String>()
                        section.templatesDB?.forEach { template ->
                            template.questionsDb?.forEach { question ->
                                question.optionsDb?.forEach {
                                    roomDb.practiceAnswerOption().insert(it)
                                }
                                roomDb.practiceQuestion().insert(question)
                            }
                            template.itemMainAudioPath?.let {
                                if (it.isNotBlank()) {
                                    links.add(it)
                                }
                                template.itemMainAudioPath =
                                    (VariablesKao.globalApplication.filesDir.absolutePath + it).replace(
                                        "//",
                                        "/"
                                    )
                            }
                            roomDb.practiceTemplate().insert(template)
                        }
                        roomDb.practiceSection().insert(section)

                        dialog.dismiss()
                        LogUtilKt.i("links:$links")
                        if (links.isNotEmpty()) {
                            downloadingId = unitSection.id
                            val url =
                                RemoteAPIDataService.BASE_URL + "client/section/${section.id}/files"
                            LogUtilKt.i("url:$url")
                            DownloadUtil.get()
                                .downloadToDataFolder(url,
                                    object : DownloadUtil.OnDownloadListener {
                                        override fun onDownloadSuccess() {
                                            //Mark download completed
                                            downloadingId = null
                                            unitSection.downloaded = true
                                            roomDb.practiceSection().insert(unitSection)
                                            main_downloading_progress.max = 100
                                            main_downloading_progress.secondaryProgress = 0
                                            loadTargets(currentTargetId ?: 1)
                                            openUnit(unitSection, book)
                                            ActivityUtils.showToastFromThread(mContext, "数据下载完成")
                                        }

                                        override fun onDownloading(
                                            progress: Int,
                                            total: Long
                                        ) {
                                            LogUtilKt.i("process:$progress / $total")
                                            main_downloading_progress.max =
                                                (total % Int.MAX_VALUE).toInt()
                                            main_downloading_progress.secondaryProgress = progress
                                        }

                                        override fun onDownloadFailed() {
                                            downloadingId = null
                                            unitSection.downloaded = false
                                            unitSection.version -= 1
                                            roomDb.practiceSection().insert(unitSection)
                                            main_downloading_progress.max = 100
                                            main_downloading_progress.secondaryProgress = 0
                                            ActivityUtils.showToastFromThread(mContext, "数据下载失败")
                                        }

                                    })
                            ActivityUtils.showToastFromThread(mContext, "开始下载文件")
                        } else {
                            loadTargets()
                            openUnit(unitSection, book)
                            ActivityUtils.showToastFromThread(mContext, "数据更新完成")
                        }

                    }
            }, {})

        } else {
            openUnit(unitSection, book)
        }


    }

    fun openUnit(unitSection: PracticeSection, book: PracticeBook) {
        var intent = Intent(mContext, LianBookUnitSummaryActivity::class.java)
        intent.putExtra("section", unitSection)
        intent.putExtra("book", book)
        startActivity(intent)
    }

    override fun refresh() {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        dialogBuilder.setTitle("正在刷新列表")
        dialogBuilder.setCancelable(false)
        dialogBuilder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.show()

        CoroutineScope(Dispatchers.IO).launch {
            var roomDb = RoomDB.get(mContext.applicationContext)
            RemoteAPIDataService.apis.getTargets().onErrorReturn {
                mutableListOf<PracticeTarget>()
            }
                .subscribe { targets ->

                    var links = mutableListOf<String>()
                    var updatedOnlineTargets = mutableListOf<PracticeTarget>()
                    var updatedOnlineBooks = mutableListOf<PracticeBook>()
                    var updatedOnlineUnits = mutableListOf<PracticeSection>()

                    targets?.let {
                        it.filter { onlineTarget ->
                            var targetDb = roomDb.practiceTarget().getById(onlineTarget.id!!)
                            targetDb?.let {
                                (onlineTarget.version ?: 0) > (it.version ?: 0)
                            } ?: true
                        }.map {
                            it.downloaded = false
                            roomDb.practiceTarget().insert(it)
                            updatedOnlineTargets.add(it)
                        }

                        it.forEach {
                            it.booksDb?.filter { onlineBook ->
                                var bookDb = roomDb.practiceBook().getById(onlineBook.id!!)
                                bookDb?.let {
                                    (onlineBook.version ?: 0) > (it.version ?: 0)
                                } ?: true
                            }?.forEach { onlineBook ->
                                onlineBook.downloaded = false
                                onlineBook.coverImagePath?.let {
                                    if (it.isNotBlank()) {
                                        var path =
                                            (VariablesKao.globalApplication.filesDir.absolutePath + it).replace(
                                                "//",
                                                "/"
                                            )
                                        onlineBook.coverImagePath = path
                                        links.add(it)
                                    }
                                }

                                roomDb.practiceBook().insert(onlineBook)
                                updatedOnlineBooks.add(onlineBook)
                            }
                        }

                        it.flatMap {
                            it.booksDb?.flatMap { it.unitSectionsDb ?: mutableListOf() }
                                ?: mutableListOf()
                        }.filter { onlineUnit ->
                            var sectionDb = roomDb.practiceSection().getById(onlineUnit.id!!)
                            sectionDb?.let {
                                (onlineUnit.version ?: 0) > (it.version ?: 0)
                            } ?: true
                        }?.forEach {
                            it.downloaded = false
                            roomDb.practiceSection().insert(it)
                            updatedOnlineUnits.add(it)
                        }
                    }

                    dialog.dismiss()
                    //download book covers file
                    if (links.isNotEmpty()) {
                        downloadingId = 1
                        val url =
                            RemoteAPIDataService.BASE_URL + "client/targets/bookCovers"
                        LogUtilKt.i("url:$url")
                        DownloadUtil.get()
                            .downloadToDataFolder(url,
                                object : DownloadUtil.OnDownloadListener {
                                    override fun onDownloadSuccess() {
                                        //Mark download completed
                                        LogUtilKt.i("Download book covers done")
                                        downloadingId = null
                                        loadTargets()
                                        main_downloading_progress.max = 100
                                        main_downloading_progress.secondaryProgress = 0
                                        ActivityUtils.showToastFromThread(mContext, "数据下载完成")
                                    }

                                    override fun onDownloading(
                                        progress: Int,
                                        total: Long
                                    ) {
                                        LogUtilKt.i("process:$progress / $total")
                                        main_downloading_progress.max =
                                            (total % Int.MAX_VALUE).toInt()
                                        main_downloading_progress.secondaryProgress = progress
                                    }

                                    override fun onDownloadFailed() {
                                        LogUtilKt.i("Download book covers failed")
                                        downloadingId = null
                                        roomDb.practiceTarget().getAll()?.forEach { target ->
                                            target.version?.let {
                                                target.version -= 1
                                                roomDb.practiceTarget().insert(target)
                                            }
                                        }

                                        loadTargets()
                                        main_downloading_progress.max = 100
                                        main_downloading_progress.secondaryProgress = 0
                                        ActivityUtils.showToastFromThread(mContext, "数据下载失败")
                                    }

                                })

                    }

                    if (updatedOnlineBooks.isNullOrEmpty() && updatedOnlineTargets.isNullOrEmpty() && updatedOnlineUnits.isNullOrEmpty()) {
                        loadTargets()
                        ActivityUtils.showToastFromThread(mContext, "数据更新完成")
                    } else {
                        if (links.isNotEmpty()) {
                            ActivityUtils.showToastFromThread(mContext, "开始下载文件")
                        } else {
                            loadTargets()
                            ActivityUtils.showToastFromThread(mContext, "数据更新完成")
                        }
                    }
                }
        }

    }

    override fun onResume() {
        super.onResume()
        loadTargets(currentTargetId ?: 1)
    }

    override fun gradeChanged() {
        loadTargets(currentTargetId ?: 1)
    }

}

interface OnExcercistStartListener {
    fun start(book: PracticeBook, unitSection: PracticeSection)
}