package com.viastub.kao100.beans

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
    var shortName: String
)

data class TestPaperTag(
    var id: Int,
    var name: String
)