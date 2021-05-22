package com.viastub.kao100.config.db.init

import android.content.Context
import com.viastub.kao100.db.ConfigGlobal
import com.viastub.kao100.db.RoomDB

class ConfigGlobalLoader : DataLoader {
    override fun load(applicationContext: Context, roomDb: RoomDB): Int {
        var provinces =
            "全国,安徽省,北京市,重庆市,福建省,甘肃省,广东省,广西,贵州省,海南省,河北省,河南省,黑龙江省,湖北省,湖南省,江西省,吉林省,江苏省,辽宁省,内蒙古,宁夏,青海省,山西省,山东省,陕西省,上海市,四川省,天津市,西藏,新疆,云南省,浙江省"
        var types = "全部测试,高考,中考,期末考试,期中考试"

        var grades = "所有年级,高三,高二,高一,初三,初二,初一,六年级(预备),五年级,四年级,三年级,二年级,一年级"

        roomDb.configGlobal().insert(ConfigGlobal(1, ConfigGlobal.key_provinces, provinces))
        roomDb.configGlobal().insert(ConfigGlobal(2, ConfigGlobal.key_types, types))
        roomDb.configGlobal().insert(ConfigGlobal(3, ConfigGlobal.key_grades, grades))

        return 3
    }

}