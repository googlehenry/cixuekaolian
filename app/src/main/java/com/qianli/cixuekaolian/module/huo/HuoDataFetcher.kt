package com.qianli.cixuekaolian.module.huo

import com.qianli.cixuekaolian.base.BaseBean
import com.qianli.cixuekaolian.http.RetrofitService
import com.yechaoa.yutilskt.LogUtilKt
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HuoDataFetcher(huo: HuoDataUpdater) {

    private var mHuoDataUpdater: HuoDataUpdater = huo

    fun getArticleList(page: Int) {

        RetrofitService.getApiService()
            .getArticleList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<Article>> {
                override fun onComplete() {
                    LogUtilKt.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtilKt.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<Article>) {
                    LogUtilKt.i("onNext")
                    mHuoDataUpdater.getArticleList(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mHuoDataUpdater.getArticleError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

    fun getArticleMoreList(page: Int) {

        RetrofitService.getApiService()
            .getArticleList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<Article>> {
                override fun onComplete() {
                    LogUtilKt.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtilKt.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<Article>) {
                    LogUtilKt.i("onNext")
                    mHuoDataUpdater.getArticleMoreList(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mHuoDataUpdater.getArticleMoreError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

    fun getBanner() {
        RetrofitService.getApiService()
            .getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<MutableList<Banner>>> {
                override fun onComplete() {
                    LogUtilKt.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtilKt.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<MutableList<Banner>>) {
                    LogUtilKt.i("onNext")
                    mHuoDataUpdater.getBanner(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mHuoDataUpdater.getBannerError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

    fun collect(id: Int) {
        RetrofitService.getApiService()
            .collect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<String>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: BaseBean<String>) {
                    if (-1001 == t.errorCode) {
                        mHuoDataUpdater.login(t.errorMsg + "(°∀°)ﾉ")
                    } else {
                        mHuoDataUpdater.collect("收藏成功 (°∀°)ﾉ")
                    }
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                }
            })
    }

    fun unCollect(id: Int) {
        RetrofitService.getApiService()
            .unCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<String>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: BaseBean<String>) {
                    mHuoDataUpdater.unCollect("取消成功 (°∀°)ﾉ")
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                }
            })
    }

}
