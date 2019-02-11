package ru.geekbrains.mediaplayer.player

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import ru.geekbrains.mediaplayer.model.MediaSourceEntity
import ru.geekbrains.mediaplayer.model.MediaType

class ExoPlayerProviderImpl(private val context: Context): PlayerProvider() {

    private val listener = ExoPlayerListener()

    private val player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector()).apply {
        addListener(listener)
    }

    override fun onPlay(data: MediaSourceEntity) {
        mediaItem = data
        val source = prepareSource(data)
        player.apply {
            prepare(source)
            playWhenReady = true
        }
    }

    private fun prepareSource(data: MediaSourceEntity): ExtractorMediaSource {
        val uri = when (data.mediaType) {
            MediaType.ONLINE -> Uri.parse(data.urlPath)
            MediaType.FILE -> Uri.fromFile(data.file)
        }

        val dataSource = DefaultDataSourceFactory(context, Util.getUserAgent(context, "Mediaplayer"))
        return ExtractorMediaSource.Factory(dataSource)
            .createMediaSource(uri)
    }

    override fun onStop() {
        player.stop()
    }

    override fun onDestroy() {
        player.removeListener(listener)
        player.release()
    }

    private inner class ExoPlayerListener: Player.EventListener {
        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {}
        override fun onSeekProcessed() {}
        override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {}
        override fun onPlayerError(error: ExoPlaybackException?) {}
        override fun onLoadingChanged(isLoading: Boolean) {}
        override fun onPositionDiscontinuity(reason: Int) {}
        override fun onRepeatModeChanged(repeatMode: Int) {}
        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
        override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {}

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (player.playbackState) {
                Player.STATE_IDLE -> callbackState(false)
                Player.STATE_BUFFERING -> callbackState(true)
                Player.STATE_ENDED -> callbackState(false)
                Player.STATE_READY -> callbackState(true)
            }
        }
    }
}