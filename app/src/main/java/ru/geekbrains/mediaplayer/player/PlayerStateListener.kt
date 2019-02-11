package ru.geekbrains.mediaplayer.player

import ru.geekbrains.mediaplayer.model.MediaSourceEntity

interface PlayerStateListener {
    fun onChangePlayingState(data: MediaSourceEntity)
}