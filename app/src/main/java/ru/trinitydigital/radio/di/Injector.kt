package ru.trinitydigital.radio.di

import ru.trinitydigital.radio.RadioApp
import ru.trinitydigital.radio.data.RadioStationsRepository
import ru.trinitydigital.radio.util.MediaPlayer
import javax.inject.Inject

inline fun <T> inject(crossinline block: Injector.() -> T): Lazy<T> = lazy { Injector().block() }

class Injector {

    @Inject
    lateinit var mediaPlayer: MediaPlayer

    @Inject
    lateinit var radioStations: RadioStationsRepository

    init {
        RadioApp.app.daggerComponent.inject(this)
    }
}