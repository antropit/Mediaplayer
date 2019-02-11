package ru.geekbrains.mediaplayer.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.geekbrains.mediaplayer.model.MediaSourceEntity
import ru.geekbrains.mediaplayer.model.MediaType

interface MediaSourceRepo {
    fun getMedia(type: MediaType): Flowable<List<MediaSourceEntity>>
    fun updatePlayingState(id: Int, isPlaying:Boolean): Completable
    fun udateFileMedia(): Completable
}