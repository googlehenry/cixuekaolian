package com.viastub.kao100.config.db.init

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.viastub.kao100.R
import com.viastub.kao100.db.DictionaryConfig
import com.viastub.kao100.db.MyUser
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.TempUtil
import java.util.*


class BasicDataLoader : DataLoader {
    override fun load(applicationContext: Context, roomDb: RoomDB): Int {
        loadDictionary(roomDb)
        loadBasicUsers(roomDb, applicationContext)
        return -1
    }

    private fun loadBasicUsers(roomDb: RoomDB, applicationContext: Context) {

        var user1 = MyUser(
            1,
            UUID.randomUUID().toString().replace("-", ""),
            "viastub", "no_nick_name",
            deviceBrand = Build.BRAND,
            deviceBoard = Build.BOARD,
            deviceDevice = Build.DEVICE,
            deviceHardware = Build.HARDWARE,
            deviceManufacturer = Build.MANUFACTURER,
            deviceAndroidId = Settings.System.getString(
                applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        )
        roomDb.myUser().insert(user1)
    }

    private fun loadDictionary(roomDb: RoomDB) {
        var dict1 = DictionaryConfig(
            id = 1, title = "英汉双解词典", dictFilePath = TempUtil.loadRawFile(
                R.raw.dict_langwen_shuangyu_simple2, "dict_langwen_shuangyu_simple2.mdx"
            ),
            onlineSpeakingLinkTemplate = "http://dict.youdao.com/dictvoice?audio=#word&type=2",
            playSoundAtStart = true
        )

        var dict2 = DictionaryConfig(
            id = 2, title = "汉英大词典", dictFilePath = TempUtil.loadRawFile(
                R.raw.dict_xiandai_hanying_zonghe_dacidian,
                "dict_xiandai_hanying_zonghe_dacidian.mdx"
            ),
            onlineSpeakingLinkTemplate = "http://dict.youdao.com/dictvoice?audio=#word&type=2",
            playSoundAtStart = true
        )

        roomDb.dictionaryConfig().insert(dict1)
        roomDb.dictionaryConfig().insert(dict2)
    }
}