package ru.geekbrains.mediaplayer.data.db

import io.reactivex.Flowable
import ru.geekbrains.mediaplayer.data.MediaSourceData
import ru.geekbrains.mediaplayer.model.MediaType

interface DbProvider {

    fun getMedia(type: MediaType): Flowable<List<MediaSourceData>>
    fun setMedia(data: List<MediaSourceData>)
    fun updatePlayingState(id: Int, isPlaying: Boolean)
}