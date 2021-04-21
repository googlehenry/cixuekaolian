package com.viastub.kao100.beans

import android.os.Parcelable
import com.viastub.kao100.db.DictionaryConfig
import com.viastub.kao100.db.MyWordHistory
import kotlinx.android.parcel.Parcelize
import java.util.*

data class SearchedWord(
    val word: String,
    val source: String,
    var count: Int = 0,
    val favorite: Boolean = false
)

@Parcelize
data class CiContext(
    var dictConfig: DictionaryConfig? = null,
    var wordKeys: MutableList<String>? = null,
    var currentWordRootLinks: MutableMap<String, LinkedList<String>> = mutableMapOf(),
    var currentWordList: MutableList<String>? = mutableListOf(),
    var currentIndex: Int = 0,
    var initIndex: Int = 0,
    var currentword: String? = null,
    var currentWordHistory: MyWordHistory? = null


) : Parcelable

enum class SearchMode {
    START, MIDDLE, END, CONTAINS
}