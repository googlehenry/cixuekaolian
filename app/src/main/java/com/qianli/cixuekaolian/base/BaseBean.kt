package com.qianli.cixuekaolian.base

data class BaseBean<T>(
    val data: T,
    val errorMsg: String,
    val errorCode: Int
)
