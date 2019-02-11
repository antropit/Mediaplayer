package ru.geekbrains.mediaplayer.data.network

import io.reactivex.Flowable
import ru.geekbrains.mediaplayer.data.MediaSourceData

class NetworkProvaiderImpl(private val api:Api) : NetworkProvaider {
    override fun updateOnlineMediaData(): Flowable<List<MediaSourceData>> {
        return api.getOnlineMediaFromServer()
    }
}