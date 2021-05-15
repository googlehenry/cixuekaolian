package com.viastub.kao100.utils

import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

fun main(args: Array<String>) {
    var s = "attachment;fileName=temp_exam_4_files.zip"
    println(s.indexOf("fileName="))
//        val files: MutableList<File> = ArrayList()
//        val file = File("E:\\upload")
//        val files1 = file.listFiles()
//        for (file1 in files1) {
//            files.add(file1)
//        }
//        val file1 = File("E:\\aaa\\dd.zip")
//        zipUtil(files, file1)
//    ZipUtil.unzip("C:\\Users\\84854\\Downloads\\temp_exam_4_files.zip",
//            "C:\\Users\\84854\\Desktop\\temp\\")
}

class ZipUtil {

    companion object {

        fun concateLocalStorePath(cloudPath: String): String {
            var path =
                (VariablesKao.globalApplication.filesDir.absolutePath + cloudPath).replace(
                    "//",
                    "/"
                )
            return path
        }

        fun zip(sourceFiles: List<File>?, zipFile: File?) {
            var fos: FileOutputStream? = null
            var zos: ZipOutputStream? = null
            var fis: FileInputStream? = null
            var bis: BufferedInputStream? = null
            try {
                if (sourceFiles != null && sourceFiles.isNotEmpty()) {
                    fos = FileOutputStream(zipFile)
                    zos = ZipOutputStream(BufferedOutputStream(fos))
                    val bytes = ByteArray(1024 * 10)
                    for (file in sourceFiles) {
                        //压缩的文件名称
                        val zipEntry = ZipEntry(file.absolutePath)
                        zos.putNextEntry(zipEntry)
                        //读取压缩文件并写入压缩包
                        fis = FileInputStream(file.path)
                        bis = BufferedInputStream(fis, 1024 * 10)
                        var read = 0
                        while (bis.read(bytes, 0, 1024 * 10).also { read = it } != -1) {
                            zos.write(bytes, 0, read)
                        }
                    }
                }
            } catch (e: FileNotFoundException) {
                throw RuntimeException(e)
            } catch (e: IOException) {
                throw RuntimeException(e)
            } finally {
                try {
                    if (zos != null) {
                        zos.flush()
                        zos.close()
                    }
                    fis?.close()
                    bis?.close()
                    if (fos != null) {
                        fos.flush()
                        fos.close()
                    }
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }
        }

        fun unzip(srcfile: String, destFolder: String) {
            try {
                val zipFile = ZipFile(srcfile)
                val emu: Enumeration<*> = zipFile.entries()
                while (emu.hasMoreElements()) {
                    val entry = emu.nextElement() as ZipEntry
                    if (entry.isDirectory) {
                        File(destFolder + entry.name).mkdirs()
                        continue
                    }
                    val bis = BufferedInputStream(
                        zipFile
                            .getInputStream(entry)
                    )
                    val file = File(destFolder + entry.name)
                    val parent = file.parentFile
                    if (parent != null && !parent.exists()) {
                        parent.mkdirs()
                    }
                    var BUFFER = 1024
                    val fos = FileOutputStream(file)
                    val bos = BufferedOutputStream(fos, BUFFER)
                    var count: Int
                    val data = ByteArray(BUFFER)
                    while (bis.read(data, 0, BUFFER).also { count = it } != -1) {
                        bos.write(data, 0, count)
                    }
                    bos.flush()
                    fos.close()
                    bos.close()
                    bis.close()
                }
                zipFile.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}