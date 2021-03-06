package com.qianli.cixuekaolian.http

import com.qianli.cixuekaolian.base.BaseBean
import com.qianli.cixuekaolian.http.interceptor.AddCookiesInterceptor
import com.qianli.cixuekaolian.http.interceptor.ReceivedCookiesInterceptor
import com.qianli.cixuekaolian.module.huo.Article
import com.qianli.cixuekaolian.module.huo.Banner
import com.yechaoa.yutilskt.LogUtilKt
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

class RemoteAPI {

    interface APIS {
        @GET("banner/json")
        fun getBanner(): Observable<BaseBean<MutableList<Banner>>>

        @GET("article/list/{page}/json")
        fun getArticleList(@Path("page") page: Int): Observable<BaseBean<Article>>
    }

    companion object {
        lateinit var apis: APIS
        private const val BASE_URL = "https://www.wanandroid.com/"

        init {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(AddCookiesInterceptor())
                .addInterceptor(ReceivedCookiesInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()

            //关联okhttp并加上rxjava和gson的配置和baseurl
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API.BASE_URL)
                .build()
            apis = retrofit.create(APIS::class.java)
        }

        inline fun <T> call(
            api: () -> Observable<T>,
            crossinline success: (T) -> Unit,
            crossinline fail: (String) -> Unit
        ) {
            exec(api.invoke(), success, fail)
        }

        inline fun <T> exec(
            observable: Observable<T>,
            crossinline success: (T) -> Unit,
            crossinline fail: (String) -> Unit
        ) {
            observable.subscribeOn(Schedulers.io())
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

    }
}