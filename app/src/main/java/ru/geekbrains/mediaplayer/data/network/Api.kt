package ru.geekbrains.mediaplayer.data.network

import io.reactivex.Flowable
import retrofit2.http.GET
import ru.geekbrains.mediaplayer.data.MediaSourceData

interface Api {
    @GET("bins/1cambg")
    fun getOnlineMediaFromServer(): Flowable<List<MediaSourceData>>
}