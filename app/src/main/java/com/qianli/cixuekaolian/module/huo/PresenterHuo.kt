package com.qianli.cixuekaolian.module.huo

import com.qianli.cixuekaolian.base.BaseBean
import com.qianli.cixuekaolian.http.RetrofitService
import com.yechaoa.yutilskt.LogUtilKt
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
class PresenterHuo(huoView: IHuoView) {

    private var mIHuoView: IHuoView = huoView

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
                    mIHuoView.getArticleList(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mIHuoView.getArticleError("获取失败(°∀°)ﾉ" + e.message)
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
                    mIHuoView.getArticleMoreList(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mIHuoView.getArticleMoreError("获取失败(°∀°)ﾉ" + e.message)
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
                    mIHuoView.getBanner(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mIHuoView.getBannerError("获取失败(°∀°)ﾉ" + e.message)
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
                        mIHuoView.login(t.errorMsg + "(°∀°)ﾉ")
                    } else {
                        mIHuoView.collect("收藏成功 (°∀°)ﾉ")
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
                    mIHuoView.unCollect("取消成功 (°∀°)ﾉ")
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                }
            })
    }

}
