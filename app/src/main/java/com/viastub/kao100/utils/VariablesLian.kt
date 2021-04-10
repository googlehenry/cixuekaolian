package com.viastub.kao100.utils

import com.viastub.kao100.beans.LianContext
import com.viastub.kao100.db.PracticeTemplate

class VariablesLian {
    companion object {
        var lianContext: LianContext? = null
        var currentTemplateIdIdx: Int = -1
        var availableTemplateIds: MutableList<Int> = mutableListOf()
        var availableTemplatesMap: MutableMap<Int, PracticeTemplate> = mutableMapOf()
        var loadLastTimeSubmittedAnswers: Boolean = false
    }
}