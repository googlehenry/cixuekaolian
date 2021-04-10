package com.viastub.kao100.utils

import android.os.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class TempUtil {
    companion object {
        fun loadRawFile(resAudioFileId: Int, filename: String): String? {
            //路径：data/data/包名/file
            var defaultFileFolder: File = Variables.globalApplication.filesDir
            var outFolder: File = File(defaultFileFolder, "/demo")
            outFolder.mkdirs()
            var outFile: File = File(outFolder.absolutePath + "/$filename")


            var inputStream: InputStream =
                Variables.globalApplication.resources.openRawResource(resAudioFileId)
            var outputStream: FileOutputStream =
                FileOutputStream(outFile)
            FileUtils.copy(inputStream, outputStream)

            inputStream.close()
            outputStream.close()

            return outFile.absolutePath

        }
    }
}