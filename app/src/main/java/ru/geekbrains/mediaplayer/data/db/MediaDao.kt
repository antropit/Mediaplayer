package ru.geekbrains.mediaplayer.data.db

import android.arch.persistence.room.*
import io.reactivex.Flowable
import ru.geekbrains.mediaplayer.data.MediaSourceData
import ru.geekbrains.mediaplayer.model.MediaType

@Dao
@TypeConverters(MediaTypeConverters::class)
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedia(data: List<MediaSourceData>)

    @Query("select * from media where mediaType = :type")
    fun getMedia(type: MediaType): Flowable<List<MediaSourceData>>

    @Query("select count() from media where mediaType = :type")
    fun selectCountMedia(type: MediaType): Int

    @Query("update media set isPlaying = :isPlaying where id = :id")
    fun updatePlayingState(id: Int, isPlaying: Boolean)

    @Query("update media set isPlaying = 0")
    fun resetPlayingState()

    @Transaction
    fun updateMediaState(id: Int, isPlaying: Boolean) {
        updatePlayingState(id, isPlaying)
        resetPlayingState()
    }
}