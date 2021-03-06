package com.viastub.kao100.http

import com.viastub.kao100.beans.UserSignupResponse
import com.viastub.kao100.db.*
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

class RemoteAPIDataService {

    /*
    Retrofit请求数据返回String
    https://www.jianshu.com/p/64bbae45e479
    pay attention to the path: VariablesKao.globalApplication.filesDir 储存下载文件的SDCard目录
     */

    data class MySomeUser(
        var officialName: String?,
        var officialPassword: String?
    )

    interface APIS {


        @FormUrlEncoded
        @POST("/client/student/saveDeviceUser")
        fun signUpUser(
            @FieldMap params: MutableMap<String, Any?>
        ): Observable<UserSignupResponse>

        @GET("client/exam/{examId}")
        fun getExamById(
            @Path("examId") examId: Int
        ): Observable<ExamSimulation>

        @GET("client/section/{sectionId}")
        fun getUnitById(
            @Path("sectionId") sectionId: Int
        ): Observable<PracticeSection>

        @GET("client/inspirations")
        fun getInspirations(): Observable<List<MyInspiration>?>

        @GET("client/exams")
        fun getExams(): Observable<List<ExamSimulation>?>

        @GET("client/targets")
        fun getTargets(): Observable<List<PracticeTarget>?>

        @GET("client/books")
        fun getBooks(): Observable<List<TeachingBook>?>

        @GET("client/book/{bookId}")
        fun getBookById(
            @Path("bookId") bookId: Int
        ): Observable<TeachingBook?>
    }

    companion object {
        lateinit var apis: APIS
        const val BASE_URL = "http://viastub.com:8080/"

        init {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
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