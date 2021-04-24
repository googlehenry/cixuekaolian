package com.viastub.kao100.utils

class VariablesMy {
    companion object {
        var questionSearchMode: QuestionSearchMode = QuestionSearchMode.ERRORED
        var myCurrentSearchedWord: String? = null
    }
}

enum class QuestionSearchMode {
    ALL, FAVORITE, NOTED, ERRORED, CORRECTED, SKIPPED
}