package com.viastub.kao100.config.db.init

import android.content.Context
import com.viastub.kao100.db.RoomDB

interface DataLoader {
    fun load(applicationContext: Context, roomDb: RoomDB): Int
}