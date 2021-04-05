package com.viastub.kao100.beans

import java.util.*

data class User(
    var id: Int,
    var officialName: String,
    var nickName: String?,
    var tags: String?,
    var dateAdded: Date?
)