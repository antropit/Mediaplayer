package ru.geekbrains.mediaplayer.dl

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.mediaplayer.data.db.AppDatabase
import ru.geekbrains.mediaplayer.data.db.DbProvider
import ru.geekbrains.mediaplayer.data.db.DbProviderImpl
import ru.geekbrains.mediaplayer.data.file.FileContentProvider
import ru.geekbrains.mediaplayer.data.file.FileContentProviderImpl
import ru.geekbrains.mediaplayer.data.network.Api
import ru.geekbrains.mediaplayer.data.network.NetworkProvaider
import ru.geekbrains.mediaplayer.data.network.NetworkProvaiderImpl
import ru.geekbrains.mediaplayer.feature.list.ListViewModel
import ru.geekbrains.mediaplayer.player.MediaPlayerProviderImpl
import ru.geekbrains.mediaplayer.player.PlayerProvider
import ru.geekbrains.mediaplayer.repository.MediaSourceRepo
import ru.geekbrains.mediaplayer.repository.MediaSourceRepoImpl
import ru.geekbrains.mediaplayer.service.MediaServiceManager

val mainModule = module {
    factory { MediaPlayerProviderImpl(get()) as PlayerProvider }
    factory { MediaServiceManager(get()) }
}

val viewModelModule = module {
    viewModel { (typeName: String) -> ListViewModel(typeName, get(), get()) }
}

val dataSourceModule = module {
    single { FileContentProviderImpl() as FileContentProvider }
    single { NetworkProvaiderImpl(get()) as NetworkProvaider }
    single { DbProviderImpl(AppDatabase.get(get())) as DbProvider }
    single { MediaSourceRepoImpl(get(), get(), get()) as MediaSourceRepo }

    single { createOkHttpClient() }
    single { createWebService(get()) }
}

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