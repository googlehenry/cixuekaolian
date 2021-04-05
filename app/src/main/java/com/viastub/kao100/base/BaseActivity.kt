package com.viastub.kao100.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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
    fun launchAsync(dataAction: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            launch(Dispatchers.IO) {
                dataAction.invoke()
            }
        }
    }
}
