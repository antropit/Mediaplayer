package ru.geekbrains.mediaplayer

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.geekbrains.mediaplayer.dl.dataSourceModule
import ru.geekbrains.mediaplayer.dl.mainModule
import ru.geekbrains.mediaplayer.dl.viewModelModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(mainModule, viewModelModule, dataSourceModule))
    }
}