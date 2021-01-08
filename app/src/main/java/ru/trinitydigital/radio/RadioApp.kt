package ru.trinitydigital.radio

import android.app.Application
import androidx.viewbinding.BuildConfig
import ru.trinitydigital.radio.di.DaggerRadioComponent
import ru.trinitydigital.radio.di.RadioComponent
import ru.trinitydigital.radio.di.modules.AppModule
import timber.log.Timber
import timber.log.Timber.DebugTree

class RadioApp : Application() {

    lateinit var daggerComponent: RadioComponent

    override fun onCreate() {
        super.onCreate()
        app = this
        daggerComponent = DaggerRadioComponent
            .builder()
            .appModule(AppModule(this))
            .build()

        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())
    }

    companion object {
        lateinit var app: RadioApp
    }
}