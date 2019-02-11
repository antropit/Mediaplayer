package ru.geekbrains.mediaplayer.feature.list

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import ru.geekbrains.mediaplayer.repository.MediaSourceRepo
import ru.geekbrains.mediaplayer.service.MediaServiceManager

class ListViewModel(
    private val typeName: String,
    private val repo: MediaSourceRepo,
    private val serviceManager: MediaServiceManager
): ViewModel(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {

    }
}