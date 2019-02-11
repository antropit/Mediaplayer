package ru.geekbrains.mediaplayer.player

import android.app.*
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import io.reactivex.subjects.BehaviorSubject
import org.koin.android.ext.android.inject
import ru.geekbrains.mediaplayer.R
import ru.geekbrains.mediaplayer.feature.MainActivity
import ru.geekbrains.mediaplayer.model.MediaSourceEntity

const val NOTIFICATION_ID = 10
const val NOTIFICATION_CHANNEL_ID = "101"

class PlayerService: Service() {

    private val binder = PlayerServiceBinder()
    private val player by inject<PlayerProvider>()
    private val isPlayingStateSubject = BehaviorSubject.create<MediaSourceEntity> {  }

    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        createChannel()
    }

    fun startPlayer(data: MediaSourceEntity) {

    }

    fun stopPlayer() {

    }

    fun getPlayingStateSubject() = isPlayingStateSubject

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Media", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(text: String) {
        val notification = buildNotification(text)
        notificationManager.notify(NOTIFICATION_ID, notification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun dismissNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true)
        }
    }

    private fun buildNotification(text: String): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_play_arrow)
            .setContentTitle("let's play")
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    private inner class PlayerServiceBinder : Binder(){
        fun getService(): PlayerService{
            return this@PlayerService
        }
    }
}