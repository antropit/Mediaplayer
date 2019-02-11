package ru.geekbrains.mediaplayer.data.db

import android.arch.persistence.room.TypeConverter
import ru.geekbrains.mediaplayer.model.MediaType

class MediaTypeConverters {

    @TypeConverter
    fun mediaTypeToInt(data: MediaType): Int {
        return when(data) {
            MediaType.ONLINE -> 0
            else -> 1
        }
    }

    @TypeConverter
    fun mediaTypeFromInt(data: Int): MediaType {
        return when(data) {
            0 -> MediaType.ONLINE
            else -> MediaType.FILE
        }
    }
}