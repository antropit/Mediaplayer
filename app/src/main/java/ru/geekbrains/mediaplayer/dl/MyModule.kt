package ru.geekbrains.mediaplayer.dl

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.mediaplayer.data.network.Api

fun createOkHttpClient() : OkHttpClient {
    val httpLoggin = HttpLoggingInterceptor()
    httpLoggin.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggin)
        .build()
}

fun createWebService(okhttp: OkHttpClient) : Api {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.myjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(okhttp)
        .build()
    return retrofit.create(Api::class.java)
}