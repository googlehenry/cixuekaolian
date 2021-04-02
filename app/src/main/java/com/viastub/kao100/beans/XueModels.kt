package com.viastub.kao100.beans

data class TeachItem(
    val id: Int,
    val title: String,
    val content: String
)

data class BookItem(
    val id: Int,
    val title: String,
    val coverImg: Int
)

data class TranscriptItem(
    val id: Int,
    val seq: String? = null,
    val transcriptEnglish: String? = null,
    val transcriptChinese: String? = null,
    val title: Boolean = false
)

data class WordLine(
    val id: Int,
    val wordLine: String
)