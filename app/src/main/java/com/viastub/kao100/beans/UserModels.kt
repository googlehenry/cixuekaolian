package com.viastub.kao100.beans

import java.util.*

data class User(
    var id: Int,
    var officialName: String,
    var nickName: String?,
    var tags: String?,
    var dateAdded: Date?
)

data class MyQuestionAnsweredHistory(
    var id: Int,
    var userId: Int,
    var practiceQuestionId: Int,
    var practiceTemplateId: Int,


    )