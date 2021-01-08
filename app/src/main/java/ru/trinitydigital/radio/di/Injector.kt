package ru.trinitydigital.radio.di

import ru.trinitydigital.radio.RadioApp
import ru.trinitydigital.radio.util.MediaPlayer
import javax.inject.Inject

inline fun <T> inject(crossinline block: Injector.() -> T): Lazy<T> = lazy { Injector().block() }

class Injector {

    @Inject
    lateinit var mediaPlayer: MediaPlayer

    init {
        RadioApp.app.daggerComponent.inject(this)
    }
}