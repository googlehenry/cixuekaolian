package com.qianli.cixuekaolian.beans

import java.util.*

data class ExcerciseTarget(
    var id: Int,
    var shortName: String,
    var name: String? = null,
    var description: String? = null,
    var types: MutableList<ExcerciseByType>? = null,
    var books: MutableList<ExcerciseByBook>? = null

)

//category 1:exam/separate books for
data class ExcerciseByType(
    var id: Int,
    var shortName: String,
    var name: String? = null,
    var description: String? = null,
    var total: Int = 100,
    var error: Int = Random().nextInt(40),
    var done: Int = Math.max(Random().nextInt(total), error)
)

//category 2: textbooks' excercises
data class ExcerciseByBook(
    var id: Int,
    var shortName: String,
    var name: String? = null,
    var description: String? = null,
    var units: MutableList<ExcerciseByUnit>? = null,
    var total: Int = 100,
    var error: Int = Random().nextInt(40),
    var done: Int = Math.max(Random().nextInt(total), error)
)

data class ExcerciseByUnit(
    var id: Int,
    var shortName: String,
    var name: String? = null,
    var description: String? = null,
    var total: Int = 100,
    var error: Int = Random().nextInt(40),
    var done: Int = Math.max(Random().nextInt(total), error)
)

