package com.viastub.kao100.config.db.init

import com.viastub.kao100.db.RoomDB

interface DataLoader {
    fun load(roomDb: RoomDB): Int
}