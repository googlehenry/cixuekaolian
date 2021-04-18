package com.viastub.kao100.wigets

import android.content.Context
import android.util.AttributeSet
import android.view.ActionMode
import android.webkit.WebView


class SelectableWebView : WebView {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    override fun startActionMode(callback: ActionMode.Callback?): ActionMode {
        var actionMode = super.startActionMode(callback)
        return resolveActionMode(actionMode)
    }

    override fun startActionMode(callback: ActionMode.Callback?, type: Int): ActionMode {
        var actionMode = super.startActionMode(callback, type)
        return resolveActionMode(actionMode)
    }

    /**
     * https://www.lmlphp.com/user/18208/article/item/565739/
     */
    private fun resolveActionMode(actionMode: ActionMode): ActionMode {
        return actionMode
    }


}