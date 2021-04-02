package com.viastub.kao100.beans

data class BaseBean<T>(
    val data: T,
    val errorMsg: String,
    val errorCode: Int
)
