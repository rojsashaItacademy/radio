package ru.trinitydigital.radio.di

import dagger.Component
import ru.trinitydigital.radio.di.modules.AppModule
import ru.trinitydigital.radio.di.modules.ExoPlayerModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ExoPlayerModule::class
    ]
)
interface RadioComponent {
    fun inject(injector: Injector)
}