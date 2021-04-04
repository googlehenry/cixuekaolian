package com.viastub.kao100.utils

import android.content.Context

class Variables {
    companion object {
        lateinit var globalApplication: Context
        var currentQuestionTemplateIdIdx: Int = -1
        lateinit var availableQuestionTemplateIds: MutableList<Int>
    }
}