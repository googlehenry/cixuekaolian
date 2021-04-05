package com.viastub.kao100.utils

import android.content.Context

class Variables {
    companion object {
        lateinit var globalApplication: Context
        var currentTemplateIdIdx: Int = -1
        lateinit var availableTemplateIds: MutableList<Int>

        var currentUserId: Int = 1
    }
}