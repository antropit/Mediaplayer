package ru.geekbrains.mediaplayer.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import ru.geekbrains.mediaplayer.model.MediaSourceEntity
import ru.geekbrains.mediaplayer.model.MediaType

class MediaPlayerProvider(private val context: Context): PlayerProvider() {

    private val player = MediaPlayer()

    override fun onPlay(data: MediaSourceEntity) {
        mediaItem = data
        player.reset()

        when(data.mediaType) {
            MediaType.ONLINE -> player.setDataSource(data.urlPath)
            MediaType.FILE -> player.setDataSource(context, Uri.fromFile(data.file))
        }

        player.setOnPreparedListener {
            it.start()
            callbackState(true)
        }
        player.prepareAsync()
    }

    override fun onStop() {
        player.stop()
    }

    override fun onDestroy() {
        player.release()
    }
}