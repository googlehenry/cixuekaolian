package com.viastub.kao100.app

import android.app.Application
import com.viastub.kao100.config.db.init.ConfigGlobalLoader
import com.viastub.kao100.config.db.init.ExamDataLoader
import com.viastub.kao100.config.db.init.PracticeDataLoader
import com.viastub.kao100.config.db.init.TeachingBookDataLoader
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.Variables
import com.yechaoa.yutilskt.ActivityUtilKt
import com.yechaoa.yutilskt.LogUtilKt
import com.yechaoa.yutilskt.YUtilsKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        YUtilsKt.initialize(this)
        LogUtilKt.setIsLog(true)
        registerActivityLifecycleCallbacks(ActivityUtilKt.activityLifecycleCallbacks)
        Variables.globalApplication = applicationContext
        CoroutineScope(Dispatchers.IO).launch {
            RoomDB.get(applicationContext).let {
                ConfigGlobalLoader().load(it)
                ExamDataLoader().load(it)
                PracticeDataLoader().load(it)
                TeachingBookDataLoader().load(it)
            }
        }

    }

}
