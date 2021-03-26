package com.qianli.cixuekaolian.http

import com.qianli.cixuekaolian.http.interceptor.AddCookiesInterceptor
import com.qianli.cixuekaolian.http.interceptor.ReceivedCookiesInterceptor
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

class RemoteAPIVpnService {

    /*
    Retrofit请求数据返回String
    https://www.jianshu.com/p/64bbae45e479
     */
    interface APIS {
        @GET("vpn/status?token=cixueliankao")
        fun getVpnStatus(): Observable<ResponseBody>

        @GET("vpn/start?token=cixueliankao")
        fun vpnSart(): Observable<ResponseBody>

        @GET("vpn/stop?token=cixueliankao")
        fun vpnStop(): Observable<ResponseBody>
    }

    companion object {
        lateinit var apis: APIS
        private const val BASE_URL = "http://viastub.com/"

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
    }
}