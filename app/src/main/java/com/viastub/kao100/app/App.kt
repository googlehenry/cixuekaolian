package com.viastub.kao100.app

import android.app.Application
import com.viastub.kao100.config.db.init.BasicDataLoader
import com.viastub.kao100.config.db.init.ConfigGlobalLoader
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.exception.UnCaughtExceptionHandler
import com.viastub.kao100.utils.VariablesKao
import com.yechaoa.yutilskt.ActivityUtilKt
import com.yechaoa.yutilskt.LogUtilKt
import com.yechaoa.yutilskt.YUtilsKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UnCaughtExceptionHandler().init(applicationContext)
        YUtilsKt.initialize(this)
        LogUtilKt.setIsLog(true)
        registerActivityLifecycleCallbacks(ActivityUtilKt.activityLifecycleCallbacks)
        VariablesKao.globalApplication = applicationContext
        CoroutineScope(Dispatchers.IO).launch {
            RoomDB.get(applicationContext).let {
                ConfigGlobalLoader().load(applicationContext, it)
                BasicDataLoader().load(applicationContext, it)
//                ExamDataLoader().load(applicationContext, it)
//                PracticeDataLoader().load(applicationContext, it)
//                TeachingBookDataLoader().load(applicationContext, it)
            }
        }

    }

}
