package com.qianli.cixuekaolian.beans

data class SearchedWord(
    val word: String,
    val source: String,
    val collect: Boolean = false
)