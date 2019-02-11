package ru.geekbrains.mediaplayer.data.db

import io.reactivex.Flowable
import ru.geekbrains.mediaplayer.data.MediaSourceData
import ru.geekbrains.mediaplayer.model.MediaType

class DbProviderImpl(private val db: AppDatabase): DbProvider {
    override fun getMedia(type: MediaType): Flowable<List<MediaSourceData>> {
        return db.mediaDao().getMedia(type)
    }

    override fun setMedia(data: List<MediaSourceData>) {
        db.mediaDao().insertMedia(data)
    }

    override fun updatePlayingState(id: Int, isPlaying: Boolean) {
        db.mediaDao().updatePlayingState(id, isPlaying)
    }
}