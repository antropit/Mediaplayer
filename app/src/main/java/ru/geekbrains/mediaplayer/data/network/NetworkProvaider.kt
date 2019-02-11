package ru.geekbrains.mediaplayer.data.network

import io.reactivex.Flowable
import ru.geekbrains.mediaplayer.data.MediaSourceData

interface NetworkProvaider {
    fun updateOnlineMediaData(): Flowable<List<MediaSourceData>>
}