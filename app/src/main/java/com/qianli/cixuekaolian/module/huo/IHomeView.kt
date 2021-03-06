package com.qianli.cixuekaolian.module.huo

import com.qianli.cixuekaolian.base.BaseBean
import com.qianli.cixuekaolian.base.BaseView

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface IHomeView : BaseView {

    fun getBanner(banners: BaseBean<MutableList<Banner>>)

    fun getBannerError(msg: String)

    fun getArticleList(article: BaseBean<Article>)

    fun getArticleError(msg: String)

    fun getArticleMoreList(article: BaseBean<Article>)

    fun getArticleMoreError(msg: String)

    fun login(msg: String)

    fun collect(msg: String)

    fun unCollect(msg: String)

}