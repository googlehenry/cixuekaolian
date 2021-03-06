package com.viastub.kao100.wigets

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.forEach
import com.viastub.kao100.db.MyCollectedNote
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.module.ci.CiPage0Activity
import com.viastub.kao100.utils.ActivityUtils
import com.viastub.kao100.utils.VariablesKao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TextViewSelectionCallback(
    var context: Context,
    private var textView: TextView,
    var tags: String?
) :
    ActionMode.Callback {
    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu): Boolean {
//        menu.removeItem(android.R.id.selectAll)
//        menu.removeItem(android.R.id.cut)
//        menu.removeItem(android.R.id.copy)
//        menu.removeItem(android.R.id.shareText)

//        menu.clear()
        var tobeDelted = mutableListOf<Int>()
        menu.forEach {
            if (it.itemId in 991..999) {
            } else {
                tobeDelted.add(it.itemId)
            }
        }
        tobeDelted?.map {
            menu.removeItem(it)
        }
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu): Boolean {
        // Called when action mode is first created. The menu supplied
        // will be used to generate action buttons for the action mode

        // Here is an example MenuItem

        menu.add(0, 997, 99997, "复制")
        menu.add(0, 998, 99998, "加笔记")
        menu.add(0, 999, 99999, "查词典")


        return true
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            997 -> {
                val cm: ClipboardManager? =
                    context.getSystemService(Context.CLIPBOARD_SERVICE)
                        ?.let { it as ClipboardManager }
                val selectedText: CharSequence = getSelectedText()
                val mClipData = ClipData.newPlainText("选中文本", selectedText)
                // 将ClipData内容放到系统剪贴板里。
                cm!!.setPrimaryClip(mClipData)
                Toast.makeText(context, "选中文本已复制到剪贴板", Toast.LENGTH_SHORT).show()
                mode.finish()
                true
            }
            998 -> {
                val selectedText: CharSequence = getSelectedText()

                if (selectedText.trim().isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        RoomDB.get(context).myCollectedNote().insert(
                            MyCollectedNote(
                                userId = VariablesKao.currentUserId,
                                collectedText = selectedText.trim().toString(),
                                tags = tags
                            )
                        )
                    }
                    Toast.makeText(context, "已保存", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "未选中文本", Toast.LENGTH_SHORT).show()
                }
                mode.finish()
                true
            }
            999 -> {
                val selectedText: CharSequence = getSelectedText()

                val regex = Regex("[a-zA-Z]+[\\-\\']?[a-z]*")
                var wordListX = regex.findAll(selectedText).map { it.value }.toList()
                var sortedList = wordListX

                if (!sortedList.isNullOrEmpty()) {
                    ActivityUtils.goToDictionary(
                        context, sortedList, sortedList[0], Intent(
                            context,
                            CiPage0Activity::class.java
                        )
                    )
                    Toast.makeText(context, "查询选中单词", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "未选中单词", Toast.LENGTH_SHORT).show()
                }
                // Finish and close the ActionMode
                mode.finish()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun getSelectedText(): CharSequence {
        var min = 0
        var max: Int = textView.getText().length
        if (textView.isFocused()) {
            val selStart: Int = textView.getSelectionStart()
            val selEnd: Int = textView.getSelectionEnd()
            min = Math.max(0, Math.min(selStart, selEnd))
            max = Math.max(0, Math.max(selStart, selEnd))
        }
        // Perform your definition lookup with the selected text
        val selectedText: CharSequence = textView.getText().subSequence(min, max)
        return selectedText
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        // Called when an action mode is about to be exited and
        // destroyed
    }
}


