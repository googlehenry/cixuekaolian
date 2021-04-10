package com.viastub.kao100.beans

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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

@Parcelize
data class KaoContext(
    var type: KaoType,
    var typedEntityId: Int,
    var currentIsPartialQuestions: Boolean,
    var earnedScoresLastTime: Boolean = false,
    var earnedScoresThisTimeTemp: Double? = null,
    var previousExamSimuLoaded: Boolean = false,
    var loadLastExam: Boolean = false
) : Parcelable

enum class KaoType {
    ExamSimulation
}