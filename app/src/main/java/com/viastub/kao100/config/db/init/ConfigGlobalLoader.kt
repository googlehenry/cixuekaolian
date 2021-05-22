package com.viastub.kao100.config.db.init

import android.content.Context
import com.viastub.kao100.db.ConfigGlobal
import com.viastub.kao100.db.RoomDB

class ConfigGlobalLoader : DataLoader {
    override fun load(applicationContext: Context, roomDb: RoomDB): Int {
        var provinces =
            "全国,安徽,北京市,重庆市,福建,甘肃,广东,广西,贵州,海南,河北,河南,黑龙江,湖北,湖南,江西,吉林,江苏,辽宁,内蒙古,宁夏,青海,山西,山东,陕西,上海市,四川,天津市,西藏,新疆,云南,浙江"
        var types = "全部测试,高考,中考,期末考试,期中考试"

        var grades = "所有年级,高三,高二,高一,初三,初二,初一,六年级(预备),五年级,四年级,三年级,二年级,一年级"

        roomDb.configGlobal().insert(ConfigGlobal(1, ConfigGlobal.key_provinces, provinces))
        roomDb.configGlobal().insert(ConfigGlobal(2, ConfigGlobal.key_types, types))
        roomDb.configGlobal().insert(ConfigGlobal(3, ConfigGlobal.key_grades, grades))

        return 3
    }

}