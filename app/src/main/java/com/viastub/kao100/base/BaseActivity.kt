package com.viastub.kao100.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.viastub.kao100.db.MyCollectedNote
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.Variables
import com.viastub.kao100.wigets.CommonDialog
import com.viastub.kao100.wigets.CommonDialog.OnClickBottomListener
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * Created by yechao on 2020/1/3/003.
 * Describe :
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(LayoutInflater.from(this).inflate(id(), null))
        afterCreated()
    }


    protected fun setMyTitle(title: String) {
        supportActionBar?.title = title
    }

    protected abstract fun id(): Int
    protected abstract fun afterCreated()

    fun apiCallFailure(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    /**
     * 统一处理返回键
     */
    protected fun setBackEnabled() {
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    /**
     * 返回键
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    //Kotlin coroutine, to load data async
    fun <T> awaitAsync(dataAction: () -> T, uiAction: (result: T) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            var rs = async(Dispatchers.IO) {
                dataAction.invoke()
            }.await()
            uiAction.invoke(rs)
        }
    }

    //Kotlin coroutine, to load data async
    fun doAsync(dataAction: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            launch(Dispatchers.IO) {
                dataAction.invoke()
            }
        }
    }


    fun addNewCollectDialog(item: String) {
        val dialog = CommonDialog(this)
        if (!item.trim().isNullOrBlank() && item.split(" ").size > 1) {
            dialog.message = item
        }

        dialog
            .setTitle("收集一个新项")
            .setSingle(false).setOnClickBottomListener(object : OnClickBottomListener {
                override fun onPositiveClick() {
                    val inputName = dialog.message
                    if (inputName.split(" ").size > 1) {
                        doAsync {
                            RoomDB.get(applicationContext).myCollectedNote().insert(
                                MyCollectedNote(
                                    userId = Variables.currentUserId,
                                    collectedText = inputName
                                )
                            )
                        }
                        dialog.message = ""
                        Toast.makeText(this@BaseActivity, "该文本已添加到我的收集", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@BaseActivity, "单个单词无法收集", Toast.LENGTH_SHORT).show()
                    }

                    dialog.dismiss()
                }

                override fun onNegtiveClick() {
                    dialog.dismiss()
                }
            }).show()
    }


}
