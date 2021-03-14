package com.qianli.cixuekaolian.beans

data class ExcerciseBook(
    var id: Int,
    var shortName: String,
    var name: String? = null,
    var description: String? = null,
    var types: MutableList<ExcerciseByType>? = null,
    var chapters: MutableList<ExcerciseByChapter>? = null

)

//category 1:exam/separate books for
data class ExcerciseByType(
    var id: Int,
    var shortName: String,
    var name: String? = null,
    var description: String? = null,
    var total: Int = 0
)

//category 2: textbooks' excercises
data class ExcerciseByChapter(
    var id: Int,
    var shortName: String,
    var name: String? = null,
    var description: String? = null,
    var units: MutableList<ExcerciseByUnit>? = null
)

data class ExcerciseByUnit(
    var id: Int,
    var shortName: String,
    var name: String? = null,
    var description: String? = null,
    var total: Int = 0
)

