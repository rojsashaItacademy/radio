package ru.trinitydigital.radio.util

import android.content.Context
import android.net.Uri
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util

interface MediaPlayer {
    fun play(url: String)
    fun getExoPlayer(): ExoPlayer
}

class MediaPlayerImpl(private val context: Context) : MediaPlayer {

    private lateinit var exoMediaPlayer: ExoPlayer
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var stateBuilder: PlaybackStateCompat.Builder

    init {
        initializeMediaSession()
        initializePlayer()
    }


    override fun play(url: String) {
        val userAgent = Util.getUserAgent(
            context,
            context.getString(R.string.exo_controls_rewind_description)
        )

        val httpDataSourceFactory = DefaultHttpDataSourceFactory(
            userAgent,
            null /* listener */,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true /* allowCrossProtocolRedirects */
        )

        val mediaSource = ExtractorMediaSource.Factory(
            DefaultDataSourceFactory(
                context,
                null,
                httpDataSourceFactory
            )
        )
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(url))

        exoMediaPlayer.prepare(mediaSource)

        exoMediaPlayer.playWhenReady = true

        exoMediaPlayer.addListener(listener)

    }

    override fun getExoPlayer(): ExoPlayer {

        return exoMediaPlayer
    }

    private fun initializePlayer() {
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(context)
        exoMediaPlayer =
            ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl)
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

//        mediaSession.setCallback(SessionCallback())

        mediaSession.isActive = true
    }

    val listener = object : Player.EventListener {
        override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
            Log.d("adsasdsad", "asdsadsad")
        }

        override fun onTracksChanged(
            trackGroups: TrackGroupArray?,
            trackSelections: TrackSelectionArray?
        ) {
            Log.d("adsasdsad", "asdsadsad")
        }

        override fun onLoadingChanged(isLoading: Boolean) {
            Log.d("adsasdsad", "asdsadsad")
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            Log.d("adsasdsad", "asdsadsad")
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
                    Log.d("adsasdsad", "asdsadsad")
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            Log.d("adsasdsad", "asdsadsad")
        }

        override fun onPlayerError(error: ExoPlaybackException?) {
            Log.d("adsasdsad", "asdsadsad")
        }

        override fun onPositionDiscontinuity(reason: Int) {
            Log.d("adsasdsad", "asdsadsad")
        }

        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
            Log.d("adsasdsad", "asdsadsad")
        }

        override fun onSeekProcessed() {
            Log.d("adsasdsad", "asdsadsad")
        }

    }
}