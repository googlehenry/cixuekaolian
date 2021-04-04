package com.viastub.kao100.config.db.init

import com.viastub.kao100.db.GlobalConfigKaoFiltersProvince
import com.viastub.kao100.db.GlobalConfigKaoFiltersType
import com.viastub.kao100.db.RoomDB

class ConfigGlobalLoader : DataLoader {
    override fun load(roomDb: RoomDB): Int {
        var provinces =
            "全国,安徽省,北京市,重庆市,福建省,甘肃省,广东省,广西,贵州省,海南省,河北省,河南省,黑龙江省,湖北省,湖南省,江西省,吉林省,江苏省,辽宁省,内蒙古,宁夏,青海省,山西省,山东省,陕西省,上海市,四川省,天津市,西藏,新疆,云南省,浙江省"
        var types = "全部测试,高考,中考,期末考试,期中考试"

        var gradesAll = "所有年级,高3年级,高2年级,高1年级,初3年级,初2年级,初1年级,初中预备,6年级,5年级,4年级,3年级,2年级,1年级"
        var gradesCEE = "所有年级,高3年级"
        var gradesHEE = "所有年级,初3年级"

        var provinceData = provinces.split(",").mapIndexed { index, province ->
            GlobalConfigKaoFiltersProvince(
                index + 1,
                province,
                types
            )
        }.toMutableList()

        var typeData = mutableListOf(
            GlobalConfigKaoFiltersType(1, "全部测试", gradesAll),
            GlobalConfigKaoFiltersType(2, "高考", gradesCEE),
            GlobalConfigKaoFiltersType(3, "中考", gradesHEE),
            GlobalConfigKaoFiltersType(4, "期末考试", gradesAll),
            GlobalConfigKaoFiltersType(5, "期中考试", gradesAll),
        )



        roomDb.globalConfigKaoFiltersProvinces().insert(provinceData)
        roomDb.globalConfigKaoFiltersTypes().insert(typeData)

        return provinceData.size
    }

}