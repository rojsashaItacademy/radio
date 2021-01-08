package ru.trinitydigital.radio.data

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import ru.trinitydigital.radio.di.inject
import timber.log.Timber

class RadioService : Service() {

    private val binder by lazy { RadioBinder() }
    private val player by inject { mediaPlayer }

    override fun onCreate() {
        super.onCreate()
        player.play("https://www.last.fm/music/Cher/Believe")
    }

    override fun onBind(intent: Intent?): IBinder {
        Timber.d("onBind")
        return binder
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
        Timber.d("onDestroy")
    }
}