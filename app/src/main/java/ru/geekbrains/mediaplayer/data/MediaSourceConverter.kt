package ru.geekbrains.mediaplayer.data

import ru.geekbrains.mediaplayer.model.MediaSourceEntity
import ru.geekbrains.mediaplayer.model.MediaType
import java.io.File

fun convertMediaSourceEntity(data: MediaSourceData): MediaSourceEntity {
    return MediaSourceEntity(
        data.id,
        data.name,
        if(data.mediaType == MediaType.ONLINE) null else File(data.urlPath),
        data.urlPath,
        data.imagePath,
        data.mediaType,
        data.isPlaying
    )
}