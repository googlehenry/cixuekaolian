package com.qianli.cixuekaolian.module.huo

import com.qianli.cixuekaolian.base.BaseBean
import com.qianli.cixuekaolian.base.DataUpdater

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface HuoDataUpdater : DataUpdater {

    fun updateBanner(banners: BaseBean<MutableList<Banner>>)

    fun updateBannerError(msg: String)

    fun updateArticleList(article: BaseBean<Article>)

    fun updateArticleError(msg: String)

    fun updateArticleMoreList(article: BaseBean<Article>)

    fun updateArticleMoreError(msg: String)

    fun login(msg: String)

    fun collect(msg: String)

    fun unCollect(msg: String)

}