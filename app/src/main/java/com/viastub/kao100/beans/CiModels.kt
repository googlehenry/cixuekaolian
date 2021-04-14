package com.viastub.kao100.beans

import android.os.Parcelable
import com.viastub.kao100.db.DictionaryConfig
import kotlinx.android.parcel.Parcelize

data class SearchedWord(
    val word: String,
    val source: String,
    val collect: Boolean = false
)

@Parcelize
data class CiContext(
    var dictConfig: DictionaryConfig? = null,
    var wordKeys: MutableList<String>? = null
) : Parcelable

enum class SearchMode {
    START, MIDDLE, END
}