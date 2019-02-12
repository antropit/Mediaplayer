package ru.geekbrains.mediaplayer.feature.list

import android.arch.lifecycle.*
import android.os.Handler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.geekbrains.mediaplayer.model.MediaSourceEntity
import ru.geekbrains.mediaplayer.model.MediaType
import ru.geekbrains.mediaplayer.repository.MediaSourceRepo
import ru.geekbrains.mediaplayer.service.MediaServiceManager

class ListViewModel(
    private val typeName: String,
    private val repo: MediaSourceRepo,
    private val serviceManager: MediaServiceManager
): ViewModel(), LifecycleObserver {

    private val compDesp = CompositeDisposable()
    private var mediaItem: MediaSourceEntity? = null
    val isPlayingState = MutableLiveData<Boolean>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        serviceManager.bindService()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Handler().postDelayed({
            serviceManager.getIsPlayerState()?.run {
                compDesp.add(this
                    .doOnNext { isPlayingState.postValue(it.isPlaying) }
                    .observeOn(Schedulers.io())
                    .flatMapCompletable { repo.updatePlayingState(it.id, it.isPlaying) }
                    .subscribe()
                )
            }
        }, 1000)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        serviceManager.unbindService()
        compDesp.clear()
    }

    fun getFilesFromdisc() {
        compDesp.add(
            repo.updateFileMedia()
                .observeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun getMedia() = LiveDataReactiveStreams.fromPublisher(
        repo.getMedia(MediaType.valueOf(typeName))
            .observeOn(Schedulers.io())
    )

    fun onClickItem(data: MediaSourceEntity) {
        mediaItem = data
        serviceManager.startPlayer(data)
    }

    fun onStartStopPlayer() {
        if(isPlayingState.value!!) serviceManager.stopPlayer() else mediaItem?.also { serviceManager.startPlayer(it) }
    }
}