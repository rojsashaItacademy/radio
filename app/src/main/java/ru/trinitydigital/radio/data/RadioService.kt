package ru.trinitydigital.radio.data

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import ru.trinitydigital.radio.di.inject
import ru.trinitydigital.radio.util.NotificationUtils
import timber.log.Timber

class RadioService : Service() {

    private val binder by lazy { RadioBinder() }
    private val player by inject { mediaPlayer }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
        Log.d("Adasdasdasd", "Adadasdasdasdasd")
    }

    fun play(station: String) {
        player.play(station)
    }

    override fun onBind(intent: Intent?): IBinder {
        Timber.d("onBind")
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(1, NotificationUtils.createNotification(applicationContext))
    }

    inner class RadioBinder : Binder() {
        fun getService() = this@RadioService
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Timber.d("onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Timber.d("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.pause()
        stopSelf()
    }
}