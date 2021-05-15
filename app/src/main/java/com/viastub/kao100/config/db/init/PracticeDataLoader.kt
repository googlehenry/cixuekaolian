package com.viastub.kao100.config.db.init

import android.content.Context
import com.viastub.kao100.db.PracticeTarget
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.http.DownloadUtil
import com.viastub.kao100.http.RemoteAPIDataService
import com.viastub.kao100.utils.VariablesKao
import com.yechaoa.yutilskt.LogUtilKt


class PracticeDataLoader : DataLoader {
    override fun load(applicationContext: Context, roomDb: RoomDB): Int {
        tryPullOnlineTargetBooks(applicationContext, roomDb)
        return -1
    }

    private fun tryPullOnlineTargetBooks(applicationContext: Context, roomDb: RoomDB) {
        RemoteAPIDataService.apis.getTargets().onErrorReturn { mutableListOf<PracticeTarget>() }
            .subscribe { targets ->

                var links = mutableListOf<String>()
                targets?.let {
                    it.filter { onlineTarget ->
                        var targetDb = roomDb.practiceTarget().getById(onlineTarget.id!!)
                        targetDb?.let {
                            (onlineTarget.version ?: 0) > (it.version ?: 0)
                        } ?: true
                    }.forEach {
                        it.downloaded = false
                        roomDb.practiceTarget().insert(it)
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
//                                    if (!File(path).exists()) {
                                        onlineBook.coverImagePath = path
                                        links.add(it)
//                                    }
                                }
                            }

                            roomDb.practiceBook().insert(onlineBook)

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
                    }
                }

                //download book covers file
                if (links.isNotEmpty()) {
                    val url =
                        RemoteAPIDataService.BASE_URL + "client/targets/bookCovers"
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