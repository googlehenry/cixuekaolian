package com.viastub.kao100.config.db.init

import com.viastub.kao100.db.RoomDB

class ConfigGlobalLoader : DataLoader {
    override fun load(roomDb: RoomDB): Int {
        roomDb.globalConfig().insert(
            mutableListOf(
//            GlobalConfig(0,"com.viastub.")
            )
        )
        return 1
    }

}