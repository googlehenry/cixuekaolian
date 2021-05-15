package com.viastub.kao100.config.db.init

import android.content.Context
import com.viastub.kao100.R
import com.viastub.kao100.db.DictionaryConfig
import com.viastub.kao100.db.MyUser
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.TempUtil


class BasicDataLoader : DataLoader {
    override fun load(applicationContext: Context, roomDb: RoomDB): Int {
        loadDictionary(roomDb)
        loadBasicUsers(roomDb)
        return -1
    }

    private fun loadBasicUsers(roomDb: RoomDB) {
        var user1 = MyUser(1, "viastub", "no_nick_name")
        roomDb.myUser().insert(user1)
    }

    private fun loadDictionary(roomDb: RoomDB) {
        var dict1 = DictionaryConfig(
            id = 1, title = "英汉双解词典", dictFilePath = TempUtil.loadRawFile(
                R.raw.dict_lonman_bialinguol_simple2, "dict_en_zh.mdx"
            ),
            onlineSpeakingLinkTemplate = "http://dict.youdao.com/dictvoice?audio=#word&type=2",
            playSoundAtStart = true
        )

        roomDb.dictionaryConfig().insert(dict1)
    }
}