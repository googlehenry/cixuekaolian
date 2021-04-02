package com.viastub.kao100.beans

import java.util.*

data class ExcerciseTarget(
    var id: Int,
    var shortName: String,
    var name: String? = null,
    var description: String? = null,
    var types: MutableList<ExcerciseByType>? = null,
    var books: MutableList<ExcerciseByBook>? = null,
    var blocks: MutableList<ExcerciseByBlock>? = null,
)

data class ExcerciseByBlock(
    var id: Int,
    var shortName: String,
    var name: String? = null,
    var description: String? = null,
    var categories: MutableSet<String>? = null,
    var total: Int = 100,
    var error: Int = Random().nextInt(40),
    var done: Int = Math.max(Random().nextInt(total), error)
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
    var types: MutableList<ExcerciseByType>? = null,
    var total: Int = 100,
    var error: Int = Random().nextInt(40),
    var done: Int = Math.max(Random().nextInt(total), error)
)

data class LianItem(
    var id: Int = 0,
    var category: String? = null,
    var type: LianItemType? = null,
    var requirement: String? = null,
    var itemMainText: String? = null,//阅读,完形填空 etc
    var itemMainAudio: String? = null,//听力 etc
    var questions: MutableList<LianItemQuestion>? = null,
    var reviews: String? = null,
    var submitted: Boolean = false
)

data class LianItemQuestion(
    var id: Int? = null,
    var type: LianItemQuestionType? = null,
    var questionMainText: String? = null,
    var optionLians: MutableList<LianQuestionOption>? = null,
    var answerLians: MutableList<LianQuestionAnswer>? = null,
    var userSelectedOptions: MutableSet<Int>? = null,
    var userAnsered: MutableMap<Int, String>? = null,

    //provide correct answer for each question when checked answers
    var answerReviewed: String? = null,
    var answerExplained: String? = null,
)

data class LianQuestionAnswer(
    var id: Int,
    var answerTemplate: String? = null,
    var correctAnswers: MutableSet<String>? = null
)

data class LianQuestionOption(
    var id: Int,
    var optionMainText: String,
    var correctOption: Boolean = false
)

enum class LianItemQuestionType {
    SELECT_ONE_LEN40, SELECT_ONE_LEN10, SELECT_ONE_LEN2, SELECT_ONE_LEN1,
    FILL_ONE_LEN10, FILL_ONE_LEN40;

    companion object {
        fun get(name: String): LianItemQuestionType? {
            return values().find { it.name == name }
        }
    }
}

enum class LianItemType {
    SELECT_ONE_LEN40,
    FILL_ONE_LEN40,
    FILL_ONE_LEN10;

    companion object {
        fun get(name: String): LianItemType? {
            return values().find { it.name == name }
        }
    }
}