package com.viastub.kao100.utils

import android.content.Context
import com.viastub.kao100.beans.LianContext
import com.viastub.kao100.db.PracticeTemplate

class Variables {
    companion object {
        lateinit var globalApplication: Context

        var lianContext: LianContext? = null
        var currentTemplateIdIdx: Int = -1
        var availableTemplateIds: MutableList<Int> = mutableListOf()
        var availableTemplatesMap: MutableMap<Int, PracticeTemplate> = mutableMapOf()

        var currentUserId: Int = 1
    }
}