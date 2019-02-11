package ru.geekbrains.mediaplayer.data.file

import io.reactivex.Flowable
import ru.geekbrains.mediaplayer.data.MediaSourceData

interface FileContentProvider {
    fun getFiles(): Flowable<List<MediaSourceData>>
}