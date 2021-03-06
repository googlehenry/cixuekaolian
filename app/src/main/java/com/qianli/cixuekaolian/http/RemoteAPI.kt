package com.qianli.cixuekaolian.http

import com.qianli.cixuekaolian.beans.Article
import com.qianli.cixuekaolian.beans.Banner
import com.qianli.cixuekaolian.beans.BaseBean
import com.qianli.cixuekaolian.http.interceptor.AddCookiesInterceptor
import com.qianli.cixuekaolian.http.interceptor.ReceivedCookiesInterceptor
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
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

class RemoteAPI {

    interface APIS {
        @GET("banner/json")
        fun getBanner(): Observable<BaseBean<MutableList<Banner>>>

        //-----------------------【登录注册】----------------------

        //登录
//        @FormUrlEncoded
//        @POST("user/login")
//        fun login(
//            @Field("username") username: String?,
//            @Field("password") password: String?
//        ): Observable<BaseBean<User>>
//
//        //注册
//        @FormUrlEncoded
//        @POST("user/register")
//        fun register(
//            @Field("username") username: String?,
//            @Field("password") password: String?,
//            @Field("repassword") repassword: String?
//        ): Observable<BaseBean<User>>
//
//
//        //-----------------------【首页相关】----------------------
//
        //首页文章列表
        @GET("article/list/{page}/json")
        fun getArticleList(@Path("page") page: Int): Observable<BaseBean<Article>>


        //
//
//        //-----------------------【 体系 】----------------------
//
//        //体系数据
//        @GET("tree/json")
//        fun getTree(): Observable<BaseBean<MutableList<Tree>>>
//
//        //知识体系下的文章
//        @GET("article/list/{page}/json?")
//        fun getTreeChild(
//            @Path("page") page: Int,
//            @Query("cid") cid: Int
//        ): Observable<BaseBean<Article>>
//
//        //-----------------------【 导航 】----------------------
//
//        //导航数据
//        @GET("navi/json")
//        fun getNavi(): Observable<BaseBean<MutableList<Navi>>>
//
//
//        //-----------------------【 项目 】----------------------
//
//        //项目分类
//        @GET("project/tree/json")
//        fun getProject(): Observable<BaseBean<MutableList<Project>>>
//
//        //项目列表数据
//        @GET("project/list/{page}/json?")
//        fun getProjectChild(
//            @Path("page") page: Int,
//            @Query("cid") cid: Int
//        ): Observable<BaseBean<ProjectChild>>
//
//
//        //-----------------------【 搜索 】----------------------
//
//        //搜索
//        @FormUrlEncoded
//        @POST("article/query/{page}/json?")
//        fun getSearchList(
//            @Path("page") page: Int,
//            @Field("k") k: String
//        ): Observable<BaseBean<Article>>
//
//        //搜索热词
//        @GET("hotkey/json")
//        fun getHotkey(): Observable<BaseBean<MutableList<Hotkey>>>
//
//        //-----------------------【 收藏 】----------------------
//
//        //收藏文章列表
//        @GET("lg/collect/list/{page}/json?")
//        fun getCollectList(@Path("page") page: Int): Observable<BaseBean<Collect>>
//
        //收藏站内文章
        @POST("lg/collect/{id}/json")
        fun collect(@Path("id") id: Int): Observable<BaseBean<String>>

        //取消收藏（文章列表）
        @POST("lg/uncollect_originId/{id}/json")
        fun unCollect(@Path("id") id: Int): Observable<BaseBean<String>>
//
//        //取消收藏（我的收藏页面）
//        @FormUrlEncoded
//        @POST("lg/uncollect/{id}/json")
//        fun unCollect1(
//            @Path("id") id: Int,
//            @Field("originId") originId: Int
//        ): Observable<BaseBean<String>>
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
                .baseUrl(BASE_URL)
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