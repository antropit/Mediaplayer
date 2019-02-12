package ru.geekbrains.mediaplayer.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.geekbrains.mediaplayer.data.convertMediaSourceEntity
import ru.geekbrains.mediaplayer.data.db.DbProvider
import ru.geekbrains.mediaplayer.data.file.FileContentProvider
import ru.geekbrains.mediaplayer.data.network.NetworkProvaider
import ru.geekbrains.mediaplayer.model.MediaSourceEntity
import ru.geekbrains.mediaplayer.model.MediaType

class MediaSourceRepoImpl(
    private val dbProvider: DbProvider,
    private val networkProvider: NetworkProvaider,
    private val fileContentProvider: FileContentProvider
) : MediaSourceRepo {
    override fun getMedia(type: MediaType): Flowable<List<MediaSourceEntity>> {
        return dbProvider.getMedia(type)
            .distinctUntilChanged()
            .flatMap {list ->
                when(type) {
                    MediaType.ONLINE -> {
                        if(list.isEmpty()) {
                            networkProvider.updateOnlineMediaData()
                                .doOnNext { it.map { it.mediaType = MediaType.ONLINE } }
                                .doOnNext { dbProvider.setMedia(it) }
                        } else Flowable.fromArray(list)
                    } else -> {
                        if(list.isEmpty()) {
                            fileContentProvider.getFiles()
                                .doOnNext { dbProvider.setMedia(it) }
                        } else Flowable.fromArray(list)
                    }
                }
            }
            .map { it.map { convertMediaSourceEntity(it) } }
    }

    override fun updatePlayingState(id: Int, isPlaying: Boolean): Completable {
        return Completable.fromAction {
            dbProvider.updatePlayingState(id, isPlaying)
        }
    }

    override fun updateFileMedia(): Completable {
        return fileContentProvider.getFiles()
            .map { dbProvider.setMedia(it) }
            .ignoreElements()
    }
}