package org.jash.common.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

var token:String? = null

private val client by lazy {
    OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor { chain -> chain.proceed(token?.let { chain.request().newBuilder().addHeader("token", it).build() } ?: chain.request()) }
        .build()
}
val retrofit by lazy {
    Retrofit.Builder()
        .baseUrl("http://10.161.9.80:7012")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

inline fun <reified S> service() = retrofit.create(S::class.java)