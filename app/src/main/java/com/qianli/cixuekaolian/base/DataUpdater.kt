package com.qianli.cixuekaolian.base

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface DataUpdater {

    fun showLoading()

    fun hideLoading()

    fun onErrorCode(bean: BaseBean<Any>)

}