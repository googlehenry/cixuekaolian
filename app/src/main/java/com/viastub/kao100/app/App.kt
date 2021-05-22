package com.viastub.kao100.app

import android.app.Application
import com.viastub.kao100.exception.UnCaughtExceptionHandler
import com.viastub.kao100.utils.VariablesKao
import com.yechaoa.yutilskt.ActivityUtilKt
import com.yechaoa.yutilskt.LogUtilKt
import com.yechaoa.yutilskt.YUtilsKt

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UnCaughtExceptionHandler().init(applicationContext)
        YUtilsKt.initialize(this)
        LogUtilKt.setIsLog(true)
        registerActivityLifecycleCallbacks(ActivityUtilKt.activityLifecycleCallbacks)
        VariablesKao.globalApplication = applicationContext
    }

}
