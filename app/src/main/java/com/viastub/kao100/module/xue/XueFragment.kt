package com.viastub.kao100.module.xue

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.viastub.kao100.R
import com.viastub.kao100.adapter.BookItemAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.db.TeachingBook
import com.viastub.kao100.http.DownloadUtil
import com.viastub.kao100.http.RemoteAPIDataService
import com.viastub.kao100.utils.ActivityUtils
import com.viastub.kao100.utils.ZipUtil
import com.yechaoa.yutilskt.LogUtilKt
import kotlinx.android.synthetic.main.fragment_xue.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class XueFragment : BaseFragment(), View.OnClickListener {

    override fun id(): Int {
        return R.layout.fragment_xue
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        loadBooks()
    }

    private fun loadBooks() {
        doAsync(0, {
            var roomDb = RoomDB.get(mContext)
            roomDb.teachingBook().getAll()?.toMutableList() ?: mutableListOf()
        }, {
            var adapter = BookItemAdapter(this)
            adapter.data = it
            recycler_view_textbooks.layoutManager = GridLayoutManager(mContext, 3)
            recycler_view_textbooks.adapter = adapter
        })
    }

    override fun onClick(v: View?) {
        var bookDb: TeachingBook = v?.getTag(R.id.book_item_holder)?.let { it as TeachingBook }!!
        if (!bookDb.downloaded) {
            doAsync(0, {
                var roomDb = RoomDB.get(mContext)

                RemoteAPIDataService.apis.getBookById(bookDb.id!!).onErrorReturn {
                    bookDb
                }
                    .subscribe { onlineBookx ->

                        onlineBookx?.let { onlineBook ->
                            var links = mutableListOf<String>()
                            onlineBook?.unitsDb?.filter { onlineUnit ->
                                var unitDb =
                                    roomDb.teachingBookUnitSection().getById(onlineUnit.id!!)
                                unitDb?.let {
                                    (onlineUnit.version ?: 0) > (it.version ?: 0)
                                } ?: true
                            }?.forEach { onlineUnit ->
                                onlineUnit.unitCoverImagePath?.let {
                                    if (it.isNotBlank()) {
                                        var path =
                                            ZipUtil.concateLocalStorePath(it)
                                        onlineUnit.unitCoverImagePath = path
                                        links.add(it)
                                    }
                                }
                                var mappedPathsAudios = onlineUnit.audioPaths()?.mapNotNull {
                                    if (it.isNotBlank()) {
                                        var path =
                                            ZipUtil.concateLocalStorePath(it)
                                        if (!File(path).exists()) {
                                            links.add(it)
                                        }
                                        path
                                    } else {
                                        ""
                                    }
                                }?.toMutableList()?.filter { it.isNotBlank() }?.toMutableList()
                                onlineUnit.bindAudiosPaths(mappedPathsAudios)

                                var mappedPathsPages = onlineUnit.pageSnapshotPaths()?.mapNotNull {
                                    if (it.isNotBlank()) {
                                        var path =
                                            ZipUtil.concateLocalStorePath(it)
                                        if (!File(path).exists()) {
                                            links.add(it)
                                        }
                                        path
                                    } else {
                                        ""
                                    }
                                }?.toMutableList()?.filter { it.isNotBlank() }?.toMutableList()
                                onlineUnit.bindPageSnapshopPaths(mappedPathsPages)

                                onlineUnit.bookTranslationsDb?.let {
                                    roomDb.teachingTranslation().insert(it)
                                }

                                onlineUnit.bookTeachingPointsDb?.let {
                                    roomDb.teachingPoint().insert(it)
                                }

                                onlineUnit.bookWordItemsDb?.let {
                                    roomDb.teachingBookWord().insert(it)
                                }

                                roomDb.teachingBookUnitSection().insert(onlineUnit)
                            }



                            LogUtilKt.i("links:$links")
                            if (links.isNotEmpty()) {
                                val url =
                                    RemoteAPIDataService.BASE_URL + "client/book/${bookDb.id}/files"
                                LogUtilKt.i("url:$url")
                                DownloadUtil.get()
                                    .downloadToDataFolder(url,
                                        object : DownloadUtil.OnDownloadListener {
                                            override fun onDownloadSuccess() {
                                                //Mark download completed
                                                bookDb.downloaded = true
                                                roomDb.teachingBook().insert(bookDb)
                                                main_downloading_progress.max = 100
                                                main_downloading_progress.secondaryProgress = 0
                                                openBook(bookDb)
                                                refresh()
                                                ActivityUtils.showToastFromThread(
                                                    mContext,
                                                    "数据下载完成"
                                                )
                                            }

                                            override fun onDownloading(
                                                progress: Int,
                                                total: Long
                                            ) {
                                                LogUtilKt.i("process:$progress / $total")
                                                main_downloading_progress.max =
                                                    (total % Int.MAX_VALUE).toInt()
                                                main_downloading_progress.secondaryProgress =
                                                    progress
                                            }

                                            override fun onDownloadFailed() {
                                                main_downloading_progress.max = 100
                                                main_downloading_progress.secondaryProgress = 0
                                                ActivityUtils.showToastFromThread(
                                                    mContext,
                                                    "数据下载失败"
                                                )
                                            }

                                        })

                            } else {
                                bookDb.downloaded = true
                                roomDb.teachingBook().insert(bookDb)
                                openBook(bookDb)
                                ActivityUtils.showToastFromThread(mContext, "数据更新完成")
                            }
                        }


                    }
            }, {})

        } else {
            openBook(bookDb)

        }
    }

    private fun openBook(book: TeachingBook?) {
        book?.let {
            var intent = Intent(mContext, XuePage0Activity::class.java)
            intent.putExtra("teachingBook", it)
            startActivity(intent)
        }
    }

    override fun refresh() {
        loadBooks()
        CoroutineScope(Dispatchers.IO).launch {
            var roomDb = RoomDB.get(mContext.applicationContext)
            RemoteAPIDataService.apis.getBooks().onErrorReturn {
                mutableListOf<TeachingBook>()
            }
                .subscribe { books ->
                    var links = mutableListOf<String>()
                    var onlineUpdatedBooks = books?.let {
                        it.filter { onlineBook ->
                            var bookDB = roomDb.teachingBook().getBookById(onlineBook.id!!)
                            bookDB?.let {
                                (onlineBook.version ?: 0) > (it.version ?: 0)
                            } ?: true
                        }.map { onlineBook ->
                            onlineBook.downloaded = false
                            onlineBook.bookCoverImagePath?.let {
                                if (it.isNotBlank()) {
                                    var path =
                                        ZipUtil.concateLocalStorePath(it)
                                    onlineBook.bookCoverImagePath = path
                                    links.add(it)
                                }
                            }

                            roomDb.teachingBook().insert(onlineBook)
                            onlineBook
                        }
                    }


                    if (links.isNotEmpty()) {
                        val url =
                            RemoteAPIDataService.BASE_URL + "client/books/covers"
                        LogUtilKt.i("url:$url")
                        DownloadUtil.get()
                            .downloadToDataFolder(url,
                                object : DownloadUtil.OnDownloadListener {
                                    override fun onDownloadSuccess() {
                                        //Mark download completed
                                        LogUtilKt.i("Download book covers done")
                                        loadBooks()
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
                                        loadBooks()
                                        main_downloading_progress.max = 100
                                        main_downloading_progress.secondaryProgress = 0
                                        ActivityUtils.showToastFromThread(mContext, "数据下载失败")
                                    }

                                })

                    }

                    if (onlineUpdatedBooks.isNullOrEmpty()) {
                        ActivityUtils.showToastFromThread(mContext, "服务器没有更新数据")
                    } else {
                        if (links.isNotEmpty()) {
                            ActivityUtils.showToastFromThread(mContext, "开始下载文件")
                        } else {
                            loadBooks()
                            ActivityUtils.showToastFromThread(mContext, "数据更新完成")
                        }
                    }

                }

        }

    }

}