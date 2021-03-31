package com.qianli.cixuekaolian.app

import android.app.Application
import com.yechaoa.yutilskt.ActivityUtilKt
import com.yechaoa.yutilskt.LogUtilKt
import com.yechaoa.yutilskt.YUtilsKt

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        YUtilsKt.initialize(this)
        LogUtilKt.setIsLog(true)
        registerActivityLifecycleCallbacks(ActivityUtilKt.activityLifecycleCallbacks)
//        CoroutineScope(Dispatchers.Default).launch {
//            var db: DB = DB.get(applicationContext, "local_db_01")
//            db.sectionDao().delete(Section(1,2,"RANDOM","第一单元", 0.0,0.0))
//            db.section().insert(Section(1,2,"RANDOM","第一单元", 0.0,0.0))
//            LogUtilKt.i("db.record.0:" + DB.db!!.section().getAll().get(0).name)
//        }
    }

}
