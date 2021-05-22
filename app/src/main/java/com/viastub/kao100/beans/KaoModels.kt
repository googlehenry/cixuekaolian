package com.viastub.kao100.beans

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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