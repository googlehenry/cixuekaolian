package com.qianli.cixuekaolian.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.yechaoa.yutilskt.ToastUtilKt

/**
 * Created by yechao on 2020/1/3/003.
 * Describe :
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(LayoutInflater.from(this).inflate(getLayoutId(), null))
        createPresenter()
        initView()
    }

    protected fun setMyTitle(title: String) {
        supportActionBar?.title = title
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun createPresenter()
    protected abstract fun initView()

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

}
