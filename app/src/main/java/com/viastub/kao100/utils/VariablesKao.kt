package com.viastub.kao100.utils

import android.content.Context
import com.viastub.kao100.beans.KaoContext
import com.viastub.kao100.db.PracticeTemplate

class VariablesKao {
    companion object {
        lateinit var globalApplication: Context

        var kaoContext: KaoContext? = null
        var currentTemplateIdIdx: Int = -1
        var availableTemplateIds: MutableList<Int> = mutableListOf()
        var availableTemplatesMap: MutableMap<Int, PracticeTemplate> = mutableMapOf()

        var currentUserId: Int = 1
    }
}