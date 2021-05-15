package com.viastub.kao100.utils

import android.os.FileUtils
import com.viastub.kao100.db.TeachingBookWord
import com.viastub.kao100.db.TeachingPoint
import java.io.*

class TempUtil {
    companion object {
        fun loadRawFile(resAudioFileId: Int, filename: String): String? {
            //路径：data/data/包名/file
            var defaultFileFolder: File = VariablesKao.globalApplication.filesDir
            var outFolder: File = File(defaultFileFolder, "/demo")
            if (!outFolder.exists()) {
                outFolder.mkdirs()
            }
            var outFile: File = File(outFolder.absolutePath + "/$filename")
            if (outFile.exists() && outFile.length() > 100) {
                return outFile.absolutePath
            }


            var inputStream: InputStream =
                VariablesKao.globalApplication.resources.openRawResource(resAudioFileId)
            var outputStream: FileOutputStream =
                FileOutputStream(outFile)
            FileUtils.copy(inputStream, outputStream)

            inputStream.close()
            outputStream.close()

            return outFile.absolutePath

        }

        fun loadTeachingPoints(pointResourceId: Int): MutableList<TeachingPoint> {
            var points = mutableListOf<TeachingPoint>()
            var reader = BufferedReader(
                InputStreamReader(
                    (VariablesKao.globalApplication.resources.openRawResource(pointResourceId))
                )
            )
            var temp = TeachingPoint()
            temp.explained = ""
            reader.readLines().mapIndexed { index, line ->
                if (line.trim().startsWith("##")) {
                    temp.id = index

                    if (!temp.point.isNullOrBlank()) {
                        points.add(temp)
                        temp = TeachingPoint()
                        temp.explained = ""
                    }

                    temp.point = line.substring(2)
                } else {
                    if (line.isNotBlank()) {
                        temp.explained += line + "\n"
                    }
                }
            }
            reader.close()

            return points
        }

        fun loadTeachingWords(wordResourceId: Int): MutableList<TeachingBookWord> {
            //Word_Level|Word_Spelling|Word_Pronoun|Word_Grammer_Type|Word_Translation
            var words = mutableListOf<TeachingBookWord>()
            var reader = BufferedReader(
                InputStreamReader(
                    (VariablesKao.globalApplication.resources.openRawResource(wordResourceId))
                )
            )
            reader.readLines().mapIndexed { index, line ->
                var eles = line.split("|")
                if (eles.size == 5) {
                    words.add(TeachingBookWord(index, eles[1], eles[0], eles[2], eles[3], eles[4]))
                }
            }
            reader.close()

            return words
        }
    }
}