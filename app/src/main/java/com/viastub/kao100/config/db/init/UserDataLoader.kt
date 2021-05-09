package com.viastub.kao100.config.db.init

import com.viastub.kao100.db.MyUser
import com.viastub.kao100.db.RoomDB


class UserDataLoader : DataLoader {
    override fun load(roomDb: RoomDB): Int {
        loadBasicUsers(roomDb)
        return -1
    }

    private fun loadBasicUsers(roomDb: RoomDB) {
        var user1 = MyUser(1, "viastub", "no_nick_name")
        roomDb.myUser().insert(user1)
    }
}