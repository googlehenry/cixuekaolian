package com.viastub.kao100.utils

import com.viastub.kao100.beans.CiContext
import com.viastub.kao100.beans.SearchMode
import java.util.*

class VariablesCi {
    companion object {
        var ciContext: CiContext? = null
        var searchMode: SearchMode = SearchMode.START
        var autoTimer: Timer? = null
    }
}