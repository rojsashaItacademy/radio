package ru.trinitydigital.radio.data

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import ru.trinitydigital.radio.data.enums.NotificationClickTypes.*
import ru.trinitydigital.radio.di.inject
import ru.trinitydigital.radio.ui.MainViewModel
import ru.trinitydigital.radio.util.NotificationUtils
import timber.log.Timber

class RadioService : Service() {

    private val binder by lazy { RadioBinder() }
    private val player by inject { mediaPlayer }
    private val radioStations by inject { radioStations }
    private lateinit var viewModel: MainViewModel

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleClicks(intent?.action)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun handleClicks(type: String?) {
        when (type) {
            NEXT.name -> {
                nextRadio()
            }
            PLAY.name -> {
                play(radioStations.radioLiveData.value)
            }
            PREV.name -> {
                prevRadio()
            }
        }
    }

    fun chooseRadio(station: RadioStations?) {
        radioStations.radioLiveData.postValue(station)
        play(station)
    }

    fun nextRadio() {
        play(radioStations.nextStation())
    }

    fun prevRadio() {
        play(radioStations.prevStation())
    }

    private fun play(station: RadioStations?) {
        station?.station?.let { player.play(it) }
    }

    fun getActiveStation(): MutableLiveData<RadioStations> {
        return radioStations.radioLiveData
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