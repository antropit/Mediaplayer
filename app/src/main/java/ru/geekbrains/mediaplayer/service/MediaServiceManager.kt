package ru.geekbrains.mediaplayer.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import ru.geekbrains.mediaplayer.model.MediaSourceEntity

class MediaServiceManager(private val context: Context) {
    private var service: PlayerService? = null
    private var serviceBound = false

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
            service = null
            serviceBound = false
        }

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val b = binder as PlayerService.PlayerServiceBinder
            service = b.getService()
            serviceBound = true
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