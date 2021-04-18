package com.viastub.kao100.wigets

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import com.viastub.kao100.db.MyCollectedNote
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.Variables
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SelectableWebView : WebView {
    var queryWordListener: OnQueryWordRequestedListener? = null

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
        if (actionMode != null) {
            settings.javaScriptEnabled = true
            addJavascriptInterface(ActionSelectInterface(this), "JSInterface")

            val menu: Menu = actionMode.menu
            menu.clear()

            var itemCopy = menu.add(0, 997, 99997, "复制")
            var itemCollect = menu.add(0, 998, 99998, "收集")
            var itemQuery = menu.add(0, 999, 99999, "查词")

            itemQuery.setOnMenuItemClickListener {
                getSelectedDataJSAnCallBack(action = "query")
                actionMode.finish()
                true
            }
            itemCollect.setOnMenuItemClickListener {
                getSelectedDataJSAnCallBack(action = "collect")
                actionMode.finish()
                true
            }
            itemCopy.setOnMenuItemClickListener {
                getSelectedDataJSAnCallBack(action = "copy")
                actionMode.finish()
                true
            }
        }
        return actionMode
    }


    /**
     * js选中的回调接口
     */
    class ActionSelectInterface(var webview: SelectableWebView) {
        @JavascriptInterface
        fun callback(value: String?, action: String?) {
            value?.let {
                if (value.length > 0) {
                    when (action) {
                        "query" -> {
                            val selected = it
                            webview.queryWordListener?.let {
                                val regex = Regex("[a-zA-Z]+[\\-\\']?[a-z]*")
                                var wordListX = regex.findAll(selected).map { it.value }.toList()
                                var sortedList = wordListX
                                if (!sortedList.isNullOrEmpty()) {
                                    it.query(sortedList, sortedList[0])
                                    Toast.makeText(webview.context, "查询选中单词", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    Toast.makeText(webview.context, "未选中单词", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                        "collect" -> {
                            if (it.split(" ").size > 1) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    RoomDB.get(webview.context).myCollectedNote().insert(
                                        MyCollectedNote(
                                            userId = Variables.currentUserId,
                                            collectedText = it
                                        )
                                    )
                                }

                                Toast.makeText(webview.context, "选中文本已添加到我的收集", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(webview.context, "单个单词无法收集", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        "copy" -> {
                            val cm: ClipboardManager? =
                                webview.context.getSystemService(Context.CLIPBOARD_SERVICE)
                                    ?.let { it as ClipboardManager }
                            val mClipData = ClipData.newPlainText("选中文本", it)
                            // 将ClipData内容放到系统剪贴板里。
                            cm!!.setPrimaryClip(mClipData)
                            Toast.makeText(webview.context, "选中文本已复制到剪贴板", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    /**
     * 点击的时候，获取网页中选择的文本，回掉到原生中的js接口
     * @param action 传入点击的item文本，一起通过js返回给原生接口
     */
    private fun getSelectedDataJSAnCallBack(action: String? = "field") {
        val js = "(function getSelectedText() {" +
                "var txt;" +
                "var title = \"" + action + "\";" +
                "if (window.getSelection) {" +
                "txt = window.getSelection().toString();" +
                "} else if (window.document.getSelection) {" +
                "txt = window.document.getSelection().toString();" +
                "} else if (window.document.selection) {" +
                "txt = window.document.selection.createRange().text;" +
                "}" +
                "JSInterface.callback(txt,title);" +
                "})();" +
                "if (document.selection) {" +
                "document.selection.empty();" +
                "} else if (window.getSelection) {" +
                "window.getSelection().removeAllRanges();" +
                "}"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript("javascript:$js", null)
        } else {
            loadUrl("javascript:$js")
        }
    }
}

interface OnQueryWordRequestedListener {
    fun query(wordList: List<String>, word: String)
}