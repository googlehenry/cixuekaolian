package com.qianli.cixuekaolian.beans

data class Province(
    var id: Int,
    var shortName: String,
    var testTypes: MutableList<TestType>? = null
)

data class TestType(
    var id: Int,
    var shortName: String,
    var grades: MutableList<Grade>? = null
)

data class Grade(
    var id: Int,
    var phase: String,
    var shortName: String
)
