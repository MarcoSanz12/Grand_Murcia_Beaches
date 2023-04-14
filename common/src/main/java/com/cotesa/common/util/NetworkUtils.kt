package com.cotesa.common.util

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*
import java.util.concurrent.TimeUnit

class NetworkUtils {


    companion object Factory {
        val okHttpClientBuilder: OkHttpClient by lazy {
            val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            val cookieJar: CookieJar = object : CookieJar {
                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    setCookies(cookies)
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    return cookies
                }
            }
            okHttpClientBuilder.cookieJar(cookieJar)
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)

            okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
            okHttpClientBuilder.build()
        }
        val cookies: MutableList<Cookie> = arrayListOf()

        fun createClient() =
            okHttpClientBuilder

        fun clearCookies() {
            cookies.clear()
        }

        fun setCookies(cookies: List<Cookie?>?) {
            val auxCookies = ArrayList<Cookie>()
            if (cookies != null && cookies.isNotEmpty()) {
                for (cookie in cookies) {
                    if (cookie != null) {
                        auxCookies.add(cookie)
                    }
                }
            }
            synchronized(this.cookies) { this.cookies.addAll(auxCookies) }
        }

    }

}
