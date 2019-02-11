package ru.geekbrains.mediaplayer.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import ru.geekbrains.mediaplayer.model.MediaSourceEntity

class MediaServiceManager(private val context: Context) {
    private val service: PlayerService? = null
    private val serviceBound = false

    fun bindService() {
        if (!serviceBound) {
            context.bindService(
                Intent(context, PlayerService::class.java),
                serviceConnection,
                Context.BIND_AUTO_CREATE
            )
        }
    }

    fun unbindService() {
        if (serviceBound) {
            context.unbindService(serviceConnection)
        }

    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    fun startPlayer(data: MediaSourceEntity) {
        if (serviceBound) service?.startPlayer(data)
    }

    fun stopPlayer() {
        if (serviceBound) service?.stopPlayer()
    }

    fun getIsPlayerState() = service?.getIsPlayingStateSubject()
}