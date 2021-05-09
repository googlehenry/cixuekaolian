package com.viastub.kao100.exception

import android.content.Context
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class UnCaughtExceptionHandler : Thread.UncaughtExceptionHandler {
    private var uncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null
    private var mContext: Context? = null
    fun init(context: Context?) {
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
        mContext = context
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        try {
            dumpExceptionToSDcard(ex)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler!!.uncaughtException(thread, ex)
        } else {
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }

    @Throws(IOException::class)
    private fun dumpExceptionToSDcard(ex: Throwable) {
        val PATH = mContext?.filesDir!!.absolutePath
        val dir = File(PATH)
        if (!dir.exists()) {
            dir.mkdir()
        }
        val current = System.currentTimeMillis()
        val time: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(current))
        val file = File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX)
        val printWriter = PrintWriter(BufferedWriter(FileWriter(file)))
        printWriter.print(time)
        printWriter.println()
        ex.printStackTrace(printWriter)
        printWriter.close()
    }

    companion object {
        private const val FILE_NAME = "crash"
        private const val FILE_NAME_SUFFIX = ".trace"
    }
}
