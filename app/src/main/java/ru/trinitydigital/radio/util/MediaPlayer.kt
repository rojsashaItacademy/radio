package ru.trinitydigital.radio.util

import android.content.Context
import android.net.Uri
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import timber.log.Timber
import javax.inject.Inject

interface MediaPlayer {
    fun play(url: String)
    fun pause()
    fun stop()
    fun getExoPlayer(): ExoPlayer
}

class MediaPlayerImpl(private val context: Context) : MediaPlayer, Player.EventListener {

    private lateinit var exoMediaPlayer: SimpleExoPlayer
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var stateBuilder: PlaybackStateCompat.Builder
    private lateinit var transportControl: MediaControllerCompat.TransportControls

    init {
        initializeMediaSession()
        initializePlayer()
    }

    override fun play(url: String) {
        val dataSourceFactory =
            DefaultDataSourceFactory(context, Util.getUserAgent(context, "dasdsad"))

        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(url))

        exoMediaPlayer.prepare(mediaSource)
        exoMediaPlayer.playWhenReady = true
    }

    override fun pause() {
        exoMediaPlayer.playWhenReady = false
    }

    override fun stop() {
        exoMediaPlayer.stop()
    }

    override fun getExoPlayer(): ExoPlayer {
        return exoMediaPlayer
    }

    private fun initializePlayer() {

        val bandwidthMeter = DefaultBandwidthMeter()
        val trackSelectorFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(trackSelectorFactory)

        exoMediaPlayer =
            ExoPlayerFactory.newSimpleInstance(context, trackSelector)

        exoMediaPlayer.addListener(this)
    }

    private fun initializeMediaSession() {
        mediaSession = MediaSessionCompat(context, "sadasdasd")
        mediaSession.setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        )
        mediaSession.setMediaButtonReceiver(null)

        stateBuilder = PlaybackStateCompat.Builder()
            .setActions(
                PlaybackStateCompat.ACTION_PLAY or
                        PlaybackStateCompat.ACTION_PAUSE or
                        PlaybackStateCompat.ACTION_PLAY_PAUSE or
                        PlaybackStateCompat.ACTION_FAST_FORWARD or
                        PlaybackStateCompat.ACTION_REWIND
            )

        mediaSession.setPlaybackState(stateBuilder.build())

        mediaSession.setCallback(callBack)

        mediaSession.isActive = true
    }

    private val callBack = object : MediaSessionCompat.Callback() {

        override fun onPause() {
            super.onPause()
            Timber.d("asdasdasd")
        }

        override fun onStop() {
            super.onStop()
            Timber.d("asdasdasd")
        }

        override fun onPlay() {
            super.onPlay()
            Timber.d("asdasdasd")
        }
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
        Timber.d("asdasdasd")
    }

    override fun onTracksChanged(
        trackGroups: TrackGroupArray?,
        trackSelections: TrackSelectionArray?
    ) {
        Timber.d("asdasdasd")
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        Timber.d("asdasdasd")
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        Timber.d("asdasdasd")
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        Timber.d("asdasdasd")
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        Timber.d("asdasdasd")
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        Timber.d("asdasdasd")
    }

    override fun onPositionDiscontinuity(reason: Int) {
        Timber.d("asdasdasd")
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        Timber.d("asdasdasd")
    }

    override fun onSeekProcessed() {
        Timber.d("asdasdasd")
    }

}