package com.viastub.kao100.http

import com.viastub.kao100.utils.VariablesKao
import com.viastub.kao100.utils.ZipUtil
import com.yechaoa.yutilskt.LogUtilKt
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class DownloadUtil private constructor() {
    private val okHttpClient: OkHttpClient = OkHttpClient()

    /**
     * @param url 下载连接
     * @param VariablesKao.globalApplication.filesDir 储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    fun downloadToDataFolder(url: String, listener: OnDownloadListener) {
        val request: Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                listener.onDownloadFailed()
            }

            override fun onResponse(call: Call?, response: Response) {
                var fileNameHeader = response.header("Content-Disposition")
                LogUtilKt.i("fileNameHeader:$fileNameHeader")
                fileNameHeader?.let {
                    var len = "fileName=".length
                    var startIdx = fileNameHeader.indexOf("fileName=")
                    LogUtilKt.i("startIdx:$startIdx , $it")
                    if (startIdx >= 0) {
                        var fileName = it.substring(startIdx + len)

                        var inputStream: InputStream? = null
                        val buf = ByteArray(2048)
                        var len = 0
                        var fos: FileOutputStream? = null
                        // 储存下载文件的目录
                        try {
                            inputStream = response.body()!!.byteStream()
                            val total: Long = response.body()!!.contentLength()
                            val file = File(VariablesKao.globalApplication.filesDir, fileName)
                            fos = FileOutputStream(file)
                            var sum: Long = 0
                            while (inputStream.read(buf).also { len = it } != -1) {
                                fos.write(buf, 0, len)
                                sum += len.toLong()
                                val progress = sum.toInt()
                                // 下载中
                                listener.onDownloading(progress, total)
                            }
                            fos.flush()

                            ZipUtil.unzip(
                                file.absolutePath,
                                VariablesKao.globalApplication.filesDir.absolutePath
                            )
                            // 下载完成
                            listener.onDownloadSuccess()
                        } catch (e: Exception) {
                            listener.onDownloadFailed()
                        } finally {
                            try {
                                inputStream?.close()
                            } catch (e: IOException) {
                            }
                            try {
                                fos?.close()
                            } catch (e: IOException) {
                            }
                        }

                    }
                }


            }
        })
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException
     * 判断下载目录是否存在
     */
    @Throws(IOException::class)
    private fun isExistDir(saveDir: String): String {
        // 下载位置
        val downloadFile = File(VariablesKao.globalApplication.filesDir, saveDir)
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile()
        }
        return downloadFile.getAbsolutePath()
    }

    interface OnDownloadListener {
        /**
         * 下载成功
         */
        fun onDownloadSuccess()

        /**
         * @param progress
         * 下载进度
         */
        fun onDownloading(progress: Int, total: Long)

        /**
         * 下载失败
         */
        fun onDownloadFailed()
    }

    companion object {
        private var downloadUtil: DownloadUtil? = null
        fun get(): DownloadUtil {
            if (downloadUtil == null) {
                downloadUtil = DownloadUtil()
            }
            return downloadUtil!!
        }

        /**
         * @param url
         * @return
         * 从下载连接中解析出文件名
         */
        fun getNameFromUrl(url: String): String {
            return url.substring(url.lastIndexOf("/") + 1)
        }
    }

}