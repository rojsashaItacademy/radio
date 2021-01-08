package ru.trinitydigital.radio.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.trinitydigital.radio.util.MediaPlayer
import ru.trinitydigital.radio.util.MediaPlayerImpl
import javax.inject.Singleton

@Module
class ExoPlayerModule {

    @Provides
    @Singleton
    fun provideExoPlayer(context: Context): MediaPlayer {
        return MediaPlayerImpl(context)
    }
}