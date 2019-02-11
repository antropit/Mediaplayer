package ru.geekbrains.mediaplayer.model

import java.io.File

data class MediaSourceEntity(
    var id: Int,
    var name: String,
    var file: File?,
    var urlPath: String,
    var imagePath: String,
    var mediaType: MediaType,
    var isPlaying: Boolean
    )