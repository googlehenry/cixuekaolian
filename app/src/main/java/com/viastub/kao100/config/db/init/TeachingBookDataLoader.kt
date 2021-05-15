package com.viastub.kao100.config.db.init

import android.content.Context
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.db.TeachingBook
import com.viastub.kao100.http.DownloadUtil
import com.viastub.kao100.http.RemoteAPIDataService
import com.viastub.kao100.utils.ZipUtil
import com.yechaoa.yutilskt.LogUtilKt


class TeachingBookDataLoader : DataLoader {
    override fun load(applicationContext: Context, roomDb: RoomDB): Int {
        pullOnlineTextbooks(applicationContext, roomDb)
        return -1
    }

    fun pullOnlineTextbooks(applicationContext: Context, roomDb: RoomDB) {
        RemoteAPIDataService.apis.getBooks().onErrorReturn { mutableListOf<TeachingBook>() }
            .subscribe { books ->
                var links = mutableListOf<String>()
                books?.let {
                    it.filter { onlineBook ->
                        var bookDB = roomDb.teachingBook().getBookById(onlineBook.id!!)
                        bookDB?.let {
                            (onlineBook.version ?: 0) > (it.version ?: 0)
                        } ?: true
                    }.forEach { onlineBook ->
                        onlineBook.downloaded = false
                        onlineBook.bookCoverImagePath?.let {
                            if (it.isNotBlank()) {
                                var path =
                                    ZipUtil.concateLocalStorePath(it)
                                onlineBook.bookCoverImagePath = path
//                                if (!File(path).exists()) {
                                links.add(it)
//                                }
                            }
                        }

                        roomDb.teachingBook().insert(onlineBook)
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
                                }

                                override fun onDownloading(
                                    progress: Int,
                                    total: Long
                                ) {
                                    LogUtilKt.i("process:$progress / $total")
                                }

                                override fun onDownloadFailed() {
                                    LogUtilKt.i("Download book covers failed")
                                }

                            })

                }

            }

    }


}
