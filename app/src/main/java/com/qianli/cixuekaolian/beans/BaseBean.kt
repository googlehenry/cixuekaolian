package com.qianli.cixuekaolian.beans

data class BaseBean<T>(
    val data: T,
    val errorMsg: String,
    val errorCode: Int
)
