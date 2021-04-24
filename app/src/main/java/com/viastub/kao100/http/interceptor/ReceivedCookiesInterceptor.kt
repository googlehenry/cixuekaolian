package com.viastub.kao100.http.interceptor

import com.viastub.kao100.config.MyConfig
import com.yechaoa.yutilskt.SpUtilKt
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class ReceivedCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val cookies: HashSet<String> = HashSet()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            SpUtilKt.setStringSet(MyConfig.COOKIE, cookies)
        }
        return originalResponse
    }
}