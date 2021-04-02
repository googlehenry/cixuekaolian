package com.viastub.kao100.utils

import com.yechaoa.yutilskt.LogUtilKt
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


fun <T> Observable<T>.and(success: (T) -> Unit, fail: (String) -> Unit) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<T> {
            override fun onComplete() {
                LogUtilKt.i("onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                LogUtilKt.i("onSubscribe")
            }

            override fun onNext(t: T) {
                LogUtilKt.i("onNext")
                success.invoke(t)
            }

            override fun onError(e: Throwable) {
                LogUtilKt.i("onError", e.message ?: "null")
                fail.invoke("获取失败(°∀°)ﾉ" + e.message)
            }
        })
}